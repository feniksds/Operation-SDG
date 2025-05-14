import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

public class SimulatorUI extends JFrame {
    private JPanel mainPanel;
    private JPanel optionsPanel;
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JLabel statsLabel;
    private State currentState;
    private StudentStats stats;
    private List<LogEntry> logEntries;

    public SimulatorUI() {
        // Initialize student stats
        stats = new StudentStats();
        logEntries = new ArrayList<LogEntry>();

        // Set up the frame
        setTitle("Eco Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create main panel with a layered approach
        mainPanel = new JPanel(new BorderLayout());

        // Create title label
        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create a layered pane for image and options
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null); // Using null layout for absolute positioning
        mainPanel.add(layeredPane, BorderLayout.CENTER);

        // Create image label (will be at the bottom layer)
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);

// Create semi-transparent panel for options (will be on top layer)
        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setOpaque(false); // Make it transparent

// Add some padding inside the panel to give more space to the content
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

// Make the panel use more horizontal space
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        layeredPane.add(optionsPanel, JLayeredPane.PALETTE_LAYER);

        // Create stats label
        statsLabel = new JLabel("", SwingConstants.LEFT);
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(statsLabel, BorderLayout.EAST);

        // Add resize listener to adjust component bounds
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = layeredPane.getWidth();
                int height = layeredPane.getHeight();

                // Set image label to fill the entire layered pane
                imageLabel.setBounds(0, 0, width, height);

                // Make options panel wider and estimate appropriate height
                int optionsPanelWidth = Math.min(700, width - 40); // Increase max width further

                // Get the preferred height but ensure it's at least 300px for enough space
                int preferredHeight = Math.max(optionsPanel.getPreferredSize().height + 20, 80);

                // Calculate position from bottom with enough margin
                int bottomMargin = 50; // Space from bottom of screen
                int yPosition = height - preferredHeight - bottomMargin;

                // Ensure panel doesn't go off the top of the screen
                yPosition = Math.max(20, yPosition);

                optionsPanel.setBounds(
                        (width - optionsPanelWidth) / 2,  // Centered horizontally
                        yPosition,                        // Position from bottom with margin
                        optionsPanelWidth,
                        preferredHeight
                );

                // Update the image sizing
                updateImageForState();
            }
        });

        // Add panel to frame
        add(mainPanel);

        // Start with the first state
        setCurrentState(new StartState());  //new SubscribeState()
    }

    private void setCurrentState(State state) {
        this.currentState = state;
        updateUI();
    }

    private void updateUI() {
        // Update title
        titleLabel.setText(currentState.getBeschrijving());

        // Clear previous options
        optionsPanel.removeAll();

        String inputType = currentState.getInputType();

        if ("multi".equals(inputType)) {
            // Display options in a grid layout
            Map<Integer, String> options = currentState.getOpties();
            int optionCount = options.size();

            // Determine grid dimensions based on option count
            int cols = Math.min(2, optionCount);
            int rows = (int) Math.ceil(optionCount / (double) cols);

            JPanel gridPanel = new JPanel(new GridLayout(rows, cols, 10, 10));
            gridPanel.setOpaque(false);

            for (Map.Entry<Integer, String> entry : options.entrySet()) {
                JButton button = new JButton(entry.getValue());
                button.setPreferredSize(new Dimension(150, 80));
                button.setBackground(new Color(240, 240, 240, 220));
                final int choice = entry.getKey();
                button.addActionListener(e -> {
                    State nextState = currentState.verwerkKeuze(choice, stats, logEntries);

                    System.out.printf("Keuze gemaakt: %d, volgende staat: %s%n", choice, nextState);
                    if (nextState != null) {
                        System.out.println("Next state: " + nextState.getBeschrijving());
                        setCurrentState(nextState);
                        stats.toonStats();
                    } else {
                        System.out.println("Geen volgende staat, eindigen");
                        System.out.println("Final state reached");
                        showFinalResults();
                    }
                });
                gridPanel.add(button);
            }

            optionsPanel.add(gridPanel);
        } else if ("input".equals(inputType)) {
            // Create an input field with a label and submit button
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
            inputPanel.setOpaque(false);

            JLabel promptLabel = new JLabel("Voer een getal in");
            promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            promptLabel.setFont(new Font("Arial", Font.BOLD, 30));

            JTextField inputField = new JTextField(10);
            inputField.setMaximumSize(new Dimension(150, 150));
            inputField.setPreferredSize(new Dimension(150, 25));
            inputField.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton submitButton = new JButton("Bevestigen");
            submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            submitButton.setBackground(new Color(240, 240, 240, 220));

            ActionListener submitAction = e -> {
                try {
                    int value = Integer.parseInt(inputField.getText());
                    if (value < 0) {
                        JOptionPane.showMessageDialog(this,
                                "Geef een positief getal", "Fout", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    State nextState = currentState.verwerkKeuze(value, stats, logEntries);
                    if (nextState != null) {
                        System.out.println("Next state: " + nextState.getBeschrijving());
                        setCurrentState(nextState);
                    } else {
                        System.out.println("Geen volgende staat, eindigen");
                        showFinalResults();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Ongeldige invoer. Geef een geheel getal in.", "Fout", JOptionPane.ERROR_MESSAGE);
                }
            };

            // Add the action listener to both the button and input field
            submitButton.addActionListener(submitAction);
            inputField.addActionListener(submitAction); // This makes Enter key trigger the same action

            inputPanel.add(Box.createVerticalStrut(10));
            inputPanel.add(promptLabel);
            inputPanel.add(Box.createVerticalStrut(10));
            inputPanel.add(inputField);
            inputPanel.add(Box.createVerticalStrut(10));
            inputPanel.add(submitButton);

            optionsPanel.add(inputPanel);
        }

        // Update image based on state
        updateImageForState();

        // Force recalculation of optionsPanel size and position
        Container layeredPane = optionsPanel.getParent();
        layeredPane.doLayout();

        // Refresh UI
        revalidate();
        repaint();
    }

    private void updateImageForState() {
        String imagePath = determineImagePath();
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            if (originalImage == null) {
                throw new IOException("Failed to load image");
            }

            // Get the container dimensions
            int containerWidth = imageLabel.getWidth();
            int containerHeight = imageLabel.getHeight();

            if (containerWidth <= 0 || containerHeight <= 0) {
                containerWidth = 800;
                containerHeight = 600;
            }

            // Calculate dimensions that maintain aspect ratio
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();

            double widthRatio = (double) containerWidth / originalWidth;
            double heightRatio = (double) containerHeight / originalHeight;
            double ratio = Math.min(widthRatio, heightRatio);

            int newWidth = (int) (originalWidth * ratio);
            int newHeight = (int) (originalHeight * ratio);

            System.out.println("Resizing image to: " + ratio + " : " + heightRatio);

            // Use a high-quality scaling approach
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

// Draw the original image onto the resized image
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            imageLabel.setIcon(new ImageIcon(resizedImage));
        } catch (Exception e) {
            System.err.println("Could not load image: " + imagePath);
            e.printStackTrace();
            imageLabel.setIcon(null);
            imageLabel.setText("Image not found");
        }
    }

    private String determineImagePath() {
        if (currentState != null) {
            return currentState.getImagePath();
        }
        return "images/default.png";
    }

    private void updateStatsPanel() {
        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<h2>Statistieken</h2>");
        sb.append("CO₂ uitstoot: ").append(stats.co2Uitstoot).append(" kg<br>");
        sb.append("Financiële impact: €").append(String.format("%.2f", stats.financieleImpact)).append("<br>");

        if (!stats.afvalProductie.isEmpty()) {
            sb.append("<h3>Afval:</h3>");
            for (Map.Entry<String, Double> entry : stats.afvalProductie.entrySet()) {
                sb.append(entry.getKey()).append(": ").append(String.format("%.2f", entry.getValue())).append(" kg<br>");
            }
        }
        sb.append("</body></html>");

        statsLabel.setText(sb.toString());
    }

    private void showFinalResults() {
        // Clear the main panel and create a new results panel
        mainPanel.removeAll();

        JPanel resultsPanel = new JPanel(new BorderLayout(20, 20));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        resultsPanel.setBackground(new Color(240, 248, 255)); // Light blue background

        // Create a tabbed pane to organize content
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        // ===== SUMMARY TAB =====
        JPanel summaryPanel = new JPanel(new BorderLayout(20, 20));
        summaryPanel.setBackground(resultsPanel.getBackground());

        // Top header section
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel headerLabel = new JLabel("Je Ecologische Voetafdruk");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setForeground(new Color(46, 125, 50)); // Green color
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Add congratulatory message
        JLabel subHeaderLabel = new JLabel("Simulatie voltooid! Hier is jouw milieu-impact:");
        subHeaderLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        subHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subHeaderLabel, BorderLayout.SOUTH);

        summaryPanel.add(headerPanel, BorderLayout.NORTH);

        // Center content with statistics
        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        statsPanel.setOpaque(false);



        // CO2 Stats with visual bar
        addStatWithBar(statsPanel, "CO₂ Uitstoot", stats.co2Uitstoot, "kg",
                new Color(255, 87, 34), // Orange-red for CO2
                Math.clamp(stats.co2Uitstoot / 1000.0, 0.05, 1.0)); // Scale based on value

        // Financial impact with visual bar
        double financialRatio = Math.min(Math.abs(stats.financieleImpact) / 3000.0, 1.0);
        addCenteredStatWithBar(statsPanel, "Financiële Impact", stats.financieleImpact, "€",
            new Color(33, 150, 243), // positief: blauw
            new Color(220, 0, 0),    // negatief: rood
            financialRatio, financialRatio); // Scale based on value

        // Academic impact with visual bar
        addStatWithBar(statsPanel, "Academische Impact", stats.academischeImpact, "punten",
                new Color(76, 175, 80), // Green for academic
                Math.clamp((stats.academischeImpact - (-10)) / 35.0, 0.05, 1.0)); // Scale based on value

        // Food CO2 with visual bar if there's any food CO2 tracked
        if (stats.eetCO2 > 0) {
            addStatWithBar(statsPanel, "CO₂ uit Voedsel", stats.eetCO2, "kg",
                    new Color(255, 152, 0), // Orange for food CO2
                    Math.clamp(stats.eetCO2 / 100.0, 0.05, 1.0)); // Scale based on value
        }

        // Food price with visual bar if there's any food price tracked
        if (stats.prijsVoedsel > 0) {
            addStatWithBar(statsPanel, "Uitgaven aan Voedsel", stats.prijsVoedsel, "€",
                    new Color(0, 188, 212), // Cyan for food price
                    Math.clamp(stats.prijsVoedsel / 70.0, 0.05, 1.0)); // Scale based on value
        }

        // Waste production stats
        if (!stats.afvalProductie.isEmpty()) {
            JPanel wastePanel = new JPanel(new GridLayout(0, 1, 5, 5));
            wastePanel.setOpaque(false);

            JLabel wasteLabel = new JLabel("Afvalproductie:");
            wasteLabel.setFont(new Font("Arial", Font.BOLD, 16));
            wastePanel.add(wasteLabel);

            for (Map.Entry<String, Double> entry : stats.afvalProductie.entrySet()) {
                addStatWithBar(wastePanel, entry.getKey(), entry.getValue(), "kg",
                        new Color(121, 85, 72), // Brown for waste
                        Math.clamp(entry.getValue() / 2000.0, 0.05, 1.0)); // Scale based on value
            }

            statsPanel.add(wastePanel);
        }

        // Environmental impact summary
        JPanel impactPanel = new JPanel();
        impactPanel.setOpaque(false);
        impactPanel.setLayout(new BoxLayout(impactPanel, BoxLayout.Y_AXIS));

        JLabel impactHeader = new JLabel("Jouw Milieu-Impact");
        impactHeader.setFont(new Font("Arial", Font.BOLD, 16));
        impactPanel.add(impactHeader);

        // Determine environmental rating based on CO2, waste, and food CO2
        String rating;
        Color ratingColor;

        double totalImpact = stats.co2Uitstoot;
        for (Double wasteValue : stats.afvalProductie.values()) {
            totalImpact += wasteValue * 2; // Weight waste impact
        }

        if (totalImpact < 20) {
            rating = "Uitstekend! Minimale impact";
            ratingColor = new Color(76, 175, 80); // Green
        } else if (totalImpact < 50) {
            rating = "Goed! Bewuste keuzes";
            ratingColor = new Color(139, 195, 74); // Light green
        } else if (totalImpact < 100) {
            rating = "Gemiddeld. Ruimte voor verbetering";
            ratingColor = new Color(255, 193, 7); // Amber
        } else {
            rating = "Hoog verbruik. Overweeg duurzamere keuzes";
            ratingColor = new Color(244, 67, 54); // Red
        }

        JLabel ratingLabel = new JLabel(rating);
        ratingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ratingLabel.setForeground(ratingColor);
        impactPanel.add(ratingLabel);

        statsPanel.add(impactPanel);

        JScrollPane statsScrollPane = new JScrollPane(statsPanel);
        statsScrollPane.setBorder(null);
        statsScrollPane.getViewport().setBackground(resultsPanel.getBackground());
        summaryPanel.add(statsScrollPane, BorderLayout.CENTER);

        // ===== JOURNEY TAB =====
        JPanel journeyPanel = new JPanel(new BorderLayout(20, 20));
        journeyPanel.setBackground(resultsPanel.getBackground());

        // Journey header
        JPanel journeyHeaderPanel = new JPanel(new BorderLayout());
        journeyHeaderPanel.setOpaque(false);

        JLabel journeyHeaderLabel = new JLabel("Jouw Duurzaamheidsreis");
        journeyHeaderLabel.setFont(new Font("Arial", Font.BOLD, 28));
        journeyHeaderLabel.setForeground(new Color(0, 121, 107)); // Teal color
        journeyHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        journeyHeaderPanel.add(journeyHeaderLabel, BorderLayout.CENTER);

        JLabel journeySubHeaderLabel = new JLabel("De keuzes die je hebt gemaakt en hun invloed op het milieu");
        journeySubHeaderLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        journeySubHeaderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        journeyHeaderPanel.add(journeySubHeaderLabel, BorderLayout.SOUTH);

        journeyPanel.add(journeyHeaderPanel, BorderLayout.NORTH);

        // Create timeline panel with logs
        JPanel timelinePanel = createJourneyTimeline();
        JScrollPane timelineScrollPane = new JScrollPane(timelinePanel);
        timelineScrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        timelineScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        journeyPanel.add(timelineScrollPane, BorderLayout.CENTER);

        // Add panels to tabbed pane
        tabbedPane.addTab("Samenvatting", new ImageIcon(), summaryPanel, "Bekijk je milieu-impact");
        tabbedPane.addTab("Jouw Reis", new ImageIcon(), journeyPanel, "Bekijk je duurzaamheidsreis");

        // Set custom tab colors
        tabbedPane.setBackgroundAt(0, new Color(225, 245, 254)); // Light blue for summary
        tabbedPane.setBackgroundAt(1, new Color(225, 245, 234)); // Light teal for journey

        resultsPanel.add(tabbedPane, BorderLayout.CENTER);

        // Tips area
        JTextArea tipsArea = new JTextArea();
        tipsArea.setEditable(false);
        tipsArea.setWrapStyleWord(true);
        tipsArea.setLineWrap(true);
        tipsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        tipsArea.setBackground(new Color(243, 243, 243));
        tipsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        StringBuilder tips = new StringBuilder("Tips om je impact te verminderen:\n\n");
        if (stats.co2Uitstoot > 30) {
            tips.append("• Verlaag je CO₂-uitstoot door vaker de fiets te gebruiken\n");
        }
        if (stats.financieleImpact > 50) {
            tips.append("• Overweeg tweedehands aankopen om kosten te besparen\n");
        }
        if (stats.eetCO2 > 10) {
            tips.append("• Kies vaker voor plantaardig voedsel om je voedsel-CO₂ te verlagen\n");
        }
        if (stats.eenmaligeAankopen > 100) {
            tips.append("• Overweeg of je alle eenmalige aankopen echt nodig hebt\n");
        }
        tips.append("• Recyclen en hergebruiken vermindert je afvalproductie\n");
        tips.append("• Efficiënt studeren verhoogt je academische impact");

        tipsArea.setText(tips.toString());

        // Bottom action buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setOpaque(false);

        JButton restartButton = new JButton("Opnieuw Beginnen");
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setBackground(new Color(46, 125, 50));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFocusPainted(false);
        restartButton.addActionListener(e -> {
            stats = new StudentStats();
            logEntries.clear();
            mainPanel.removeAll();
            mainPanel.setLayout(new BorderLayout());

            // Restore the original components
            mainPanel.add(titleLabel, BorderLayout.NORTH);
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setLayout(null);
            layeredPane.add(imageLabel, JLayeredPane.DEFAULT_LAYER);
            layeredPane.add(optionsPanel, JLayeredPane.PALETTE_LAYER);
            mainPanel.add(layeredPane, BorderLayout.CENTER);
            mainPanel.add(statsLabel, BorderLayout.EAST);

            setCurrentState(new EtenStartState());
        });

        JButton saveButton = new JButton("Resultaten Opslaan");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(33, 150, 243));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Resultaten opslaan");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                try {
                    File fileToSave = fileChooser.getSelectedFile();
                    if (!fileToSave.getName().endsWith(".txt")) {
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
                    }

                    java.io.PrintWriter writer = new java.io.PrintWriter(fileToSave);
                    writer.println("Eco Simulator Resultaten");
                    writer.println("========================");
                    writer.printf("CO₂-uitstoot: %.2f kg%n", stats.co2Uitstoot);
                    if (stats.eetCO2 > 0) {
                        writer.printf("CO₂ uit voedsel: %.2f kg%n", stats.eetCO2);
                    }
                    writer.printf("Financiële impact: €%.2f%n", stats.financieleImpact);
                    writer.printf("Academische impact: %.1f punten%n", stats.academischeImpact);

                    if (stats.eenmaligeAankopen > 0) {
                        writer.printf("Eenmalige aankopen: €%.2f%n", stats.eenmaligeAankopen);
                    }

                    if (stats.eetFactor != 1.0) {
                        writer.printf("Eetfactor: %.2f%n", stats.eetFactor);
                    }

                    if (stats.ritFactor > 0) {
                        writer.printf("Ritfactor: %d%n", stats.ritFactor);
                    }

                    if (stats.prijsVoedsel > 0) {
                        writer.printf("Uitgaven aan voedsel: €%.2f%n", stats.prijsVoedsel);
                    }

                    writer.println("\nAfvalproductie:");
                    for (Map.Entry<String, Double> entry : stats.afvalProductie.entrySet()) {
                        writer.printf("- %s: %.2f kg%n", entry.getKey(), entry.getValue());
                    }

                    writer.println("\nGemaakte keuzes:");
                    int choiceNum = 1;
                    for (LogEntry entry : logEntries) {
                        writer.printf("%d. %s: %s%n", choiceNum++, entry.getVraag(), entry.getGemaakteKeuze());
                    }

                    writer.println("\nAlgemene beoordeling:");
                    writer.println(rating);

                    writer.println("\nTips:");
                    writer.println(tips.toString().replace("Tips om je impact te verminderen:\n\n", ""));

                    writer.close();

                    JOptionPane.showMessageDialog(this,
                            "Resultaten opgeslagen in " + fileToSave.getName(),
                            "Opgeslagen", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                            "Fout bij opslaan: " + ex.getMessage(),
                            "Fout", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton exportImageButton = new JButton("Overzicht Exporteren");
        exportImageButton.setFont(new Font("Arial", Font.BOLD, 16));
        exportImageButton.setBackground(new Color(0, 150, 136));
        exportImageButton.setForeground(Color.WHITE);
        exportImageButton.setFocusPainted(false);
        exportImageButton.addActionListener(e -> {
            try {
                // Get current tab component to export
                Component selectedTab = tabbedPane.getSelectedComponent();
                if (selectedTab instanceof JPanel) {
                    JPanel panelToExport = (JPanel) selectedTab;

                    // Create buffered image for the panel
                    BufferedImage image = new BufferedImage(
                            panelToExport.getWidth(),
                            panelToExport.getHeight(),
                            BufferedImage.TYPE_INT_RGB);

                    Graphics2D g2d = image.createGraphics();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                    panelToExport.paint(g2d);
                    g2d.dispose();

                    // Show file chooser
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Overzicht opslaan als afbeelding");

                    // Set file filter for PNG images
                    fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                        public boolean accept(File f) {
                            return f.isDirectory() || f.getName().toLowerCase().endsWith(".png");
                        }

                        public String getDescription() {
                            return "PNG Afbeeldingen (*.png)";
                        }
                    });

                    int userSelection = fileChooser.showSaveDialog(this);

                    if (userSelection == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        if (!fileToSave.getName().endsWith(".png")) {
                            fileToSave = new File(fileToSave.getAbsolutePath() + ".png");
                        }

                        ImageIO.write(image, "png", fileToSave);

                        JOptionPane.showMessageDialog(this,
                                "Overzicht opgeslagen als " + fileToSave.getName(),
                                "Opgeslagen", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Fout bij exporteren: " + ex.getMessage(),
                        "Fout", JOptionPane.ERROR_MESSAGE);
            }
        });

        actionPanel.add(restartButton);
        actionPanel.add(Box.createHorizontalStrut(20));
        actionPanel.add(saveButton);
        actionPanel.add(Box.createHorizontalStrut(20));
        actionPanel.add(exportImageButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(tipsArea, BorderLayout.CENTER);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        resultsPanel.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(resultsPanel);

        revalidate();
        repaint();
    }

    private JPanel createJourneyTimeline() {
        // Main timeline panel with vertical BoxLayout
        JPanel timelinePanel = new JPanel();
        timelinePanel.setLayout(new BoxLayout(timelinePanel, BoxLayout.Y_AXIS));
        timelinePanel.setBackground(new Color(255, 255, 255));
        timelinePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        if (logEntries.isEmpty()) {
            JLabel emptyLabel = new JLabel("Geen keuzes geregistreerd in deze simulatie");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            timelinePanel.add(emptyLabel);
            return timelinePanel;
        }

        JLabel journeyTitleLabel = new JLabel("Je hebt " + logEntries.size() + " keuzes gemaakt");
        journeyTitleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        journeyTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timelinePanel.add(journeyTitleLabel);
        timelinePanel.add(Box.createVerticalStrut(20));

        // Add each log entry as a timeline event
        int entryNumber = 1;
        for (LogEntry entry : logEntries) {
            JPanel eventPanel = createTimelineEvent(entry, entryNumber++);
            timelinePanel.add(eventPanel);

            // Add connector line between events (except for the last one)
            if (entryNumber <= logEntries.size()) {
                JPanel connectorPanel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        g.setColor(new Color(200, 200, 200));
                        int centerX = getWidth() / 2;
                        g.drawLine(centerX, 0, centerX, getHeight());
                    }
                };
                connectorPanel.setPreferredSize(new Dimension(20, 20));
                connectorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
                connectorPanel.setOpaque(false);
                timelinePanel.add(connectorPanel);
            }
        }

        return timelinePanel;
    }

    private JPanel createTimelineEvent(LogEntry entry, int entryNumber) {
        // Create panel for the event
        JPanel eventPanel = new JPanel(new BorderLayout(15, 10));
        eventPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        eventPanel.setBackground(new Color(250, 250, 250));
        eventPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        // Create left side with number indicator
        JPanel markerPanel = new JPanel(new BorderLayout());
        markerPanel.setOpaque(false);

        JLabel numberLabel = new JLabel(String.valueOf(entryNumber));
        numberLabel.setFont(new Font("Arial", Font.BOLD, 18));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Create circular background for number
        JPanel circlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Choose a color based on entry number
                Color[] colors = {
                        new Color(46, 125, 50),    // Green
                        new Color(0, 121, 107),    // Teal
                        new Color(2, 119, 189),    // Blue
                        new Color(142, 36, 170),   // Purple
                        new Color(230, 81, 0)      // Orange
                };
                g2d.setColor(colors[(entryNumber - 1) % colors.length]);

                int diameter = Math.min(getWidth(), getHeight()) - 4;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;
                g2d.fillOval(x, y, diameter, diameter);
                g2d.dispose();
            }
        };

        circlePanel.setPreferredSize(new Dimension(40, 40));
        circlePanel.add(numberLabel);
        markerPanel.add(circlePanel, BorderLayout.CENTER);

        eventPanel.add(markerPanel, BorderLayout.WEST);

        // Create content panel for event details
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        // Question
        JLabel questionLabel = new JLabel(entry.getVraag());
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Choice
        JLabel choiceLabel = new JLabel("Jouw keuze: " + entry.getGemaakteKeuze());
        choiceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        choiceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Stats impact
        JPanel impactPanel = new JPanel();
        impactPanel.setLayout(new BoxLayout(impactPanel, BoxLayout.Y_AXIS));
        impactPanel.setOpaque(false);
        impactPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        impactPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel impactHeaderLabel = new JLabel("Impact van deze keuze:");
        impactHeaderLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        impactPanel.add(impactHeaderLabel);

        StatChange statChange = entry.getStatChange();

        // Only add stats that actually changed
        if (statChange.getCo2UitstootChange() != 0) {
            addImpactLine(impactPanel, "CO₂-uitstoot", statChange.getCo2UitstootChange(), "kg");
        }

        if (statChange.getFinancieleImpactChange() != 0) {
            addImpactLine(impactPanel, "Financiële impact", statChange.getFinancieleImpactChange(), "€");
        }

        if (statChange.getAcademischeImpactChange() != 0) {
            addImpactLine(impactPanel, "Academische impact", statChange.getAcademischeImpactChange(), "punten");
        }

        if (statChange.getAfvalProductieChange() != null && !statChange.getAfvalProductieChange().isEmpty()) {
            JLabel wasteLabel = new JLabel("Afvalproductie:");
            wasteLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            impactPanel.add(wasteLabel);

            for (Map.Entry<String, Double> wasteEntry : statChange.getAfvalProductieChange().entrySet()) {
                if (wasteEntry.getValue() != 0) {
                    addImpactLine(impactPanel, "- " + wasteEntry.getKey(), wasteEntry.getValue(), "kg");
                }
            }
        }

        // If no impacts were registered, add a note
        if (impactPanel.getComponentCount() <= 1) {
            JLabel noImpactLabel = new JLabel("Geen directe milieu-impact");
            noImpactLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            impactPanel.add(noImpactLabel);
        }

        contentPanel.add(questionLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(choiceLabel);
        contentPanel.add(impactPanel);

        eventPanel.add(contentPanel, BorderLayout.CENTER);

        return eventPanel;
    }

    private void addImpactLine(JPanel panel, String label, double value, String unit) {
        JPanel linePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        linePanel.setOpaque(false);

        JLabel textLabel = new JLabel(label + ":");
        textLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        String formattedValue;
        if (value > 0) {
            formattedValue = "+" + String.format("%.2f", value);
        } else {
            formattedValue = String.format("%.2f", value);
        }

        JLabel valueLabel = new JLabel(formattedValue + " " + unit);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 13));

        // Color-code impacts (green for positive, red for negative)
        if ((label.contains("CO₂") || label.contains("Afval")) && value > 0) {
            valueLabel.setForeground(new Color(213, 0, 0)); // Red for increased CO2/waste
        } else if ((label.contains("CO₂") || label.contains("Afval")) && value < 0) {
            valueLabel.setForeground(new Color(0, 137, 123)); // Green for decreased CO2/waste
        } else if (label.contains("Academische") && value > 0) {
            valueLabel.setForeground(new Color(0, 137, 123)); // Green for increased academic
        } else if (label.contains("Academische") && value < 0) {
            valueLabel.setForeground(new Color(213, 0, 0)); // Red for decreased academic
        } else if (label.contains("Financiële") && value > 0) {
            valueLabel.setForeground(new Color(0, 137, 123)); // Green for increased financial
        } else if (label.contains("Financiële") && value < 0) {
            valueLabel.setForeground(new Color(213, 0, 0)); // Red for decreased financial
        }

        linePanel.add(textLabel);
        linePanel.add(valueLabel);

        panel.add(linePanel);
    }


    // Helper method to add a stat with a visual bar
    private void addStatWithBar(JPanel panel, String label, double value, String unit, Color barColor, double fillRatio) {
    JPanel statPanel = new JPanel(new BorderLayout(5, 5));
    statPanel.setOpaque(false);
    statPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // wat padding

    JLabel statLabel = new JLabel(label + ": " + String.format("%.2f", value) + " " + unit);
    statLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    statLabel.setForeground(new Color(50, 50, 50)); // iets zachter grijs
    statPanel.add(statLabel, BorderLayout.NORTH);

    // Mooie bar met ronde hoeken
    JPanel barPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = 10;

            // Achtergrond
            g2.setColor(new Color(230, 230, 230));
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            // Gevulde balk
            g2.setColor(barColor);
            g2.fillRoundRect(0, 0, (int) (w * fillRatio), h, arc, arc);

            // Rand
            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);
        }
    };
    barPanel.setPreferredSize(new Dimension(200, 20));
    barPanel.setOpaque(false);
    statPanel.add(barPanel, BorderLayout.CENTER);

    panel.add(statPanel);
}

private void addCenteredStatWithBar(JPanel panel, String label, double value, String unit, Color positiveColor, Color negativeColor,
                                    double positiveFillRatio, double negativeFillRatio) {
    JPanel statPanel = new JPanel(new BorderLayout(5, 5));
    statPanel.setOpaque(false);
    statPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    JLabel statLabel = new JLabel(label + ": " + String.format("%.2f", value) + " " + unit);
    statLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    statLabel.setForeground(new Color(50, 50, 50));
    statPanel.add(statLabel, BorderLayout.NORTH);

    JPanel barPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            int arc = 10;

            g2.setColor(new Color(230, 230, 230));
            g2.fillRoundRect(0, 0, w, h, arc, arc);

            int midX = w / 2;
            int fillWidth;
            if (value < 0) {
                fillWidth = (int) ((w / 2.0) * Math.max(negativeFillRatio, 0.05));
                g2.setColor(negativeColor);
                g2.fillRoundRect(midX - fillWidth, 0, fillWidth, h, arc, arc);
            } else if (value > 0) {
                fillWidth = (int) ((w / 2.0) * Math.max(positiveFillRatio, 0.05));
                g2.setColor(positiveColor);
                g2.fillRoundRect(midX, 0, fillWidth, h, arc, arc);
            }

            g2.setColor(Color.GRAY);
            g2.drawRoundRect(0, 0, w - 1, h - 1, arc, arc);

            // Middenlijn duidelijker maken
            g2.setColor(Color.BLACK); // opvallender dan donkergrijs
            g2.setStroke(new BasicStroke(2f)); // maak de lijn 2px dik
            g2.drawLine(midX, 0, midX, h);
            g2.setStroke(new BasicStroke(1f));;
        }
    };

    barPanel.setPreferredSize(new Dimension(200, 20));
    barPanel.setOpaque(false);
    statPanel.add(barPanel, BorderLayout.CENTER);

    panel.add(statPanel);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulatorUI ui = new SimulatorUI();
            ui.setVisible(true);
        });
    }
}