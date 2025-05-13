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
        setCurrentState(new EtenStartState());
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

            submitButton.addActionListener(e -> {
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
            });

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

        resultsPanel.add(headerPanel, BorderLayout.NORTH);

        // Center content with statistics
        JPanel statsPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        statsPanel.setOpaque(false);

        // CO2 Stats with visual bar
        addStatWithBar(statsPanel, "CO₂ Uitstoot", stats.co2Uitstoot, "kg",
                new Color(255, 87, 34), // Orange-red for CO2
                Math.min(stats.co2Uitstoot / 50, 1.0)); // Scale based on value

        // Financial impact with visual bar
        addStatWithBar(statsPanel, "Financiële Impact", stats.financieleImpact, "€",
                new Color(33, 150, 243), // Blue for money
                Math.min(stats.financieleImpact / 100, 1.0)); // Scale based on value

        // Academic impact with visual bar
        addStatWithBar(statsPanel, "Academische Impact", stats.academischeImpact, "punten",
                new Color(76, 175, 80), // Green for academic
                Math.min(stats.academischeImpact / 10, 1.0)); // Scale based on value

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
                        Math.min(entry.getValue() / 5, 1.0)); // Scale based on value
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

        // Determine environmental rating based on CO2 and waste
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

        // Add tips based on stats
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
        tips.append("• Recyclen en hergebruiken vermindert je afvalproductie\n");
        tips.append("• Efficiënt studeren verlaagt je energieverbruik");

        tipsArea.setText(tips.toString());

        statsPanel.add(impactPanel);

        JScrollPane scrollPane = new JScrollPane(statsPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(resultsPanel.getBackground());
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

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

            setCurrentState(new StartState());
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
                    writer.printf("Financiële impact: €%.2f%n", stats.financieleImpact);
                    writer.printf("Academische impact: %.1f punten%n", stats.academischeImpact);
                    writer.println("\nAfvalproductie:");
                    for (Map.Entry<String, Double> entry : stats.afvalProductie.entrySet()) {
                        writer.printf("- %s: %.2f kg%n", entry.getKey(), entry.getValue());
                    }
                    writer.println("\nAlgemene beoordeling:");
                    writer.println(rating);
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

        actionPanel.add(restartButton);
        actionPanel.add(Box.createHorizontalStrut(20));
        actionPanel.add(saveButton);

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

    // Helper method to add a stat with a visual bar
    private void addStatWithBar(JPanel panel, String label, double value, String unit, Color barColor, double fillRatio) {
        JPanel statPanel = new JPanel(new BorderLayout(5, 0));
        statPanel.setOpaque(false);

        JLabel statLabel = new JLabel(label + ": " + String.format("%.2f", value) + " " + unit);
        statLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statPanel.add(statLabel, BorderLayout.NORTH);

        // Create visual bar
        JPanel barPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int w = getWidth();
                int h = getHeight();

                // Draw background
                g.setColor(new Color(220, 220, 220));
                g.fillRect(0, 0, w, h);

                // Draw filled part
                g.setColor(barColor);
                g.fillRect(0, 0, (int) (w * fillRatio), h);

                // Draw border
                g.setColor(Color.DARK_GRAY);
                g.drawRect(0, 0, w - 1, h - 1);
            }
        };
        barPanel.setPreferredSize(new Dimension(100, 15));
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