import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 750;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JLabel instructionsLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel instructionsPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    String player1Name = "Player 1";
    String player2Name = "Player 2";

    boolean gameOver = false;
    int turns = 0;
    int player1Score = 0;
    int player2Score = 0;

    TicTacToe() {
        // Show the menu frame first
        showMenuFrame();
    }

    void showMenuFrame() {
        JFrame menuFrame = new JFrame("Tic-Tac-Toe Menu");
        menuFrame.setSize(300, 350);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\elinp\\Downloads\\Tic Tac game java\\Tic Tac game\\image2.jpg");

        // Custom JPanel for background
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        menuPanel.setLayout(new GridLayout(4, 1, 10, 10));

        // Create buttons for menu options
        JButton startButton = new JButton("Start");
        JButton aboutButton = new JButton("About");
        JButton instructionsButton = new JButton("Instructions");
        JButton scoreButton = new JButton("Score");

        // Set button fonts and colors
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        startButton.setFont(buttonFont);
        aboutButton.setFont(buttonFont);
        instructionsButton.setFont(buttonFont);
        scoreButton.setFont(buttonFont);

        startButton.setForeground(Color.WHITE);
        aboutButton.setForeground(Color.WHITE);
        instructionsButton.setForeground(Color.WHITE);
        scoreButton.setForeground(Color.WHITE);

        // Make the buttons transparent
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(false);

        aboutButton.setContentAreaFilled(false);
        aboutButton.setBorderPainted(false);
        aboutButton.setOpaque(false);

        instructionsButton.setContentAreaFilled(false);
        instructionsButton.setBorderPainted(false);
        instructionsButton.setOpaque(false);

        scoreButton.setContentAreaFilled(false);
        scoreButton.setBorderPainted(false);
        scoreButton.setOpaque(false);

        startButton.setFocusPainted(false);
        aboutButton.setFocusPainted(false);
        instructionsButton.setFocusPainted(false);
        scoreButton.setFocusPainted(false);

        // Add action listeners to buttons
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuFrame.dispose();
                getPlayerNames();
            }
        });

        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAboutPage();
            }
        });

        instructionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInstructionsPage();
            }
        });

        scoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showScorePage();
            }
        });

        // Add buttons to the menu panel
        menuPanel.add(startButton);
        menuPanel.add(aboutButton);
        menuPanel.add(instructionsButton);
        menuPanel.add(scoreButton);

        // Add the menu panel to the menu frame
        menuFrame.add(menuPanel, BorderLayout.CENTER);
        menuFrame.setVisible(true);
    }

    void getPlayerNames() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        JTextField player1Field = new JTextField();
        JTextField player2Field = new JTextField();

        inputPanel.add(new JLabel("Player 1 (X):"));
        inputPanel.add(player1Field);
        inputPanel.add(new JLabel("Player 2 (O):"));
        inputPanel.add(player2Field);

        int result = JOptionPane.showConfirmDialog(frame, inputPanel, "Enter Player Names", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            player1Name = player1Field.getText().trim();
            player2Name = player2Field.getText().trim();

            if (player1Name.isEmpty()) player1Name = "Player 1";
            if (player2Name.isEmpty()) player2Name = "Player 2";

            initializeGame();
        } else {
            showMenuFrame();
        }
    }

    void initializeGame() {
        currentPlayer = playerX;
        turns = 0;
        gameOver = false;

        boardPanel.removeAll();
        textPanel.removeAll();
        instructionsPanel.removeAll();
        frame.getContentPane().removeAll();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        instructionsLabel.setText(player1Name + " (X) starts. Click a tile to place your mark.");
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        instructionsLabel.setHorizontalAlignment(JLabel.CENTER);
        instructionsPanel.setLayout(new BorderLayout());
        instructionsPanel.add(instructionsLabel);
        frame.add(instructionsPanel, BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.white);
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = new JButton();
                board[r][c].setFont(new Font("Arial", Font.BOLD, 60));
                board[r][c].setFocusPainted(false);
                board[r][c].setBackground(Color.black);
                board[r][c].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!gameOver) {
                            JButton clickedButton = (JButton) e.getSource();
                            if (clickedButton.getText().equals("")) {
                                clickedButton.setText(currentPlayer);
                                if (currentPlayer.equals(playerX)) {
                                    clickedButton.setForeground(Color.RED);
                                } else {
                                    clickedButton.setForeground(Color.BLUE);
                                }
                                turns++;
                                checkWinner();
                                if (!gameOver) {
                                    currentPlayer = (currentPlayer.equals(playerX)) ? playerO : playerX;
                                    instructionsLabel.setText("Player " + currentPlayer + "'s turn.");
                                }
                            }
                        }
                    }
                });
                boardPanel.add(board[r][c]);
            }
        }

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    void declareWinner(String winner) {
        gameOver = true;
        if (winner.equals(playerX)) {
            player1Score++;
            textLabel.setText(player1Name + " (X) wins!");
        } else if (winner.equals(playerO)) {
            player2Score++;
            textLabel.setText(player2Name + " (O) wins!");
        } else {
            textLabel.setText("It's a draw!");
        }
        showGameOverFrame(winner);
    }

    void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText()) &&
                !board[r][0].getText().isEmpty()) {
                declareWinner(board[r][0].getText());
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText()) &&
                !board[0][c].getText().isEmpty()) {
                declareWinner(board[0][c].getText());
                return;
            }
        }

        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().isEmpty()) {
            declareWinner(board[0][0].getText());
            return;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().isEmpty()) {
            declareWinner(board[0][2].getText());
            return;
        }

        if (turns == 9) {
            gameOver = true;
            textLabel.setText("It's a draw!");
            showGameOverFrame("Draw");
        }
    }

    void resetGame() {
        currentPlayer = playerX;
        turns = 0;
        gameOver = false;
        textLabel.setText("Tic-Tac-Toe");
        instructionsLabel.setText(player1Name + " (X) starts. Click a tile to place your mark.");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
            }
        }
    }

    void showGameOverFrame(String winner) {
        JFrame gameOverFrame = new JFrame("Game Over");
        gameOverFrame.setSize(400, 300);
        gameOverFrame.setLocationRelativeTo(null);
        gameOverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameOverFrame.setLayout(new BorderLayout());

        JLabel winnerLabel = new JLabel();
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        winnerLabel.setHorizontalAlignment(JLabel.CENTER);
        if (winner.equals(playerX)) {
            winnerLabel.setText(player1Name + " (X) Wins!");
        } else if (winner.equals(playerO)) {
            winnerLabel.setText(player2Name + " (O) Wins!");
        } else {
            winnerLabel.setText("It's a Draw!");
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
                gameOverFrame.dispose();
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton goBackButton = new JButton("Go Back");
        goBackButton.setFont(new Font("Arial", Font.BOLD, 16));
        goBackButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gameOverFrame.dispose();
                frame.dispose();
                showMenuFrame();
            }
        });

        buttonPanel.add(resetButton);
        buttonPanel.add(quitButton);
        buttonPanel.add(goBackButton);

        gameOverFrame.add(winnerLabel, BorderLayout.CENTER);
        gameOverFrame.add(buttonPanel, BorderLayout.SOUTH);
        gameOverFrame.setVisible(true);
    }


    void showAboutPage() {
        JOptionPane.showMessageDialog(frame,
            "Tic-Tac-Toe Game\nVersion 1.0\nDeveloped by Elin",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }

    void showInstructionsPage() {
        JOptionPane.showMessageDialog(frame,
            "How to Play:\n" +
            "1. Player X starts the game.\n" +
            "2. Click on an empty tile to place your mark.\n" +
            "3. The first player to get 3 marks in a row (horizontally, vertically, or diagonally) wins.\n" +
            "4. If all tiles are filled and no one wins, the game is a draw.",
            "Instructions",
            JOptionPane.INFORMATION_MESSAGE);
    }

    void showScorePage() {
        JOptionPane.showMessageDialog(frame,
            "Scores:\n" +
            player1Name + " (X): " + player1Score + "\n" +
            player2Name + " (O): " + player2Score,
            "Score",
            JOptionPane.INFORMATION_MESSAGE);
    }

    void showSettingsPage() {
        JPanel settingsPanel = new JPanel(new GridLayout(3, 2));
        JTextField player1Field = new JTextField(player1Name);
        JTextField player2Field = new JTextField(player2Name);

        settingsPanel.add(new JLabel("Player 1 (X):"));
        settingsPanel.add(player1Field);
        settingsPanel.add(new JLabel("Player 2 (O):"));
        settingsPanel.add(player2Field);

        int result = JOptionPane.showConfirmDialog(frame, settingsPanel, "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            player1Name = player1Field.getText().trim();
            player2Name = player2Field.getText().trim();
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
