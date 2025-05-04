import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public SimulatorUI() {
        // Initialize student stats
        stats = new StudentStats();

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
        setCurrentState(new StartState());
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
                    State nextState = currentState.verwerkKeuze(choice, stats);
                    if (nextState != null) {
                        setCurrentState(nextState);
                        stats.toonStats();
                    } else {
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
                    State nextState = currentState.verwerkKeuze(value, stats);
                    if (nextState != null) {
                        setCurrentState(nextState);
                    } else {
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
        // Clear the UI
        optionsPanel.removeAll();

        // Show final stats
        JLabel finalLabel = new JLabel("Simulatie voltooid!");
        finalLabel.setFont(new Font("Arial", Font.BOLD, 24));
        finalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsPanel.add(finalLabel);

        JButton restartButton = new JButton("Opnieuw beginnen");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            stats = new StudentStats();
            setCurrentState(new SchoolState());
        });
        optionsPanel.add(Box.createVerticalStrut(20));
        optionsPanel.add(restartButton);

        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulatorUI ui = new SimulatorUI();
            ui.setVisible(true);
        });
    }
}