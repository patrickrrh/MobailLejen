import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MobailLejenApp extends JFrame {
	
    private List<Hero> heroes;
    private List<User> users;
    private User currentUser;

    // Panels
    private JPanel loginPanel;
    private JPanel registerPanel;
    private JPanel crudPanel;

    // Labels and buttons for login
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel statusLabel;

    // Labels, buttons, and combo box for CRUD operations
    private JComboBox<String> heroComboBox;
    private JButton buyButton;
    private JButton historyButton;
    private JButton logoutButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;
    
    // Display tables and table models
    private JTable heroTable;
    private HeroTableModel heroTableModel;
    private BoughtHeroTableModel boughtHeroTableModel;
    
    public void setFrame() {
    	setTitle("Mobail Lejen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 200);
        setResizable(false);
        setLayout(new BorderLayout());
    }
    
    public void initializeHero() {
    	heroes = new ArrayList<>();
        heroes.add(new Hero("Hero A", 100));
        heroes.add(new Hero("Hero B", 200));
        heroes.add(new Hero("Hero C", 300));
    }
    
    public void initializeUser() {
    	users = new ArrayList<>();
        users.add(new Admin("admin", "admin"));
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
    }
    
    public void buildLoginPanel() {
    	loginPanel = new JPanel(new FlowLayout());

        usernameLabel = new JLabel("Username:");
        loginPanel.add(usernameLabel);
        usernameField = new JTextField(15);
        loginPanel.add(usernameField);

        passwordLabel = new JLabel("Password:");
        loginPanel.add(passwordLabel);
        passwordField = new JPasswordField(15);
        loginPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                User user = getUser(username, password);
                if (user != null) {
                    currentUser = user;
                    statusLabel.setText("Logged in as " + currentUser.getUsername());
                    switchToCRUDPanel();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }
            }
        });
        loginPanel.add(loginButton);
        
        registerButton = new JButton("Register Now");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToRegisterPanel();
            }
        });
        loginPanel.add(registerButton);

        add(loginPanel, BorderLayout.CENTER);
    }
    
    public void buildRegisterPanel() {
    	registerPanel = new JPanel(new FlowLayout());

        JLabel registerUsernameLabel = new JLabel("Username:");
        registerPanel.add(registerUsernameLabel);
        JTextField registerUsernameField = new JTextField(15);
        registerPanel.add(registerUsernameField);

        JLabel registerPasswordLabel = new JLabel("Password:");
        registerPanel.add(registerPasswordLabel);
        JPasswordField registerPasswordField = new JPasswordField(15);
        registerPanel.add(registerPasswordField);

        JButton registerConfirmButton = new JButton("Register");
        registerConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerUsernameField.getText();
                String password = new String(registerPasswordField.getPassword());

                if (getUser(username, password) == null) {
                    User user = new User(username, password);
                    users.add(user);
                    switchToLoginPanel();
                    JOptionPane.showMessageDialog(null, "Registration successful! You can now login.");
                } else {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                }
            }
        });
        registerPanel.add(registerConfirmButton);

        JButton registerCancelButton = new JButton("Cancel");
        registerCancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToLoginPanel();
            }
        });
        registerPanel.add(registerCancelButton);
    }
    
    public void addHeroActionButton(JPanel buttonsPanel) {
    	addButton = new JButton("Add Hero");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser instanceof Admin) {
                    String heroName = JOptionPane.showInputDialog(null, "Enter the name of the new hero:");
                    if (heroName != null && !heroName.isEmpty()) {
                        int heroPrice = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the price of the new hero:"));
                        Hero newHero = new Hero(heroName, heroPrice);
                        heroes.add(newHero);
                        updateHeroTable();
                        JOptionPane.showMessageDialog(null, "Hero added successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Only admins can add heroes!");
                }
            }
        });
        buttonsPanel.add(addButton);
    }
    
    public void removeHeroActionButton(JPanel buttonsPanel) {
    	removeButton = new JButton("Remove Hero");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser instanceof Admin) {
                    int selectedIndex = heroTable.getSelectedRow();
                    if (selectedIndex >= 0) {
                        Hero removedHero = heroes.remove(selectedIndex);
                        updateHeroTable();
                        JOptionPane.showMessageDialog(null, "Hero removed successfully: " + removedHero.getName());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Only admins can remove heroes!");
                }
            }
        });
        buttonsPanel.add(removeButton);
    }
    
    public void updateHeroActionButton(JPanel buttonsPanel) {
    	updateButton = new JButton("Update Hero Price");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentUser instanceof Admin) {
                    int selectedIndex = heroTable.getSelectedRow();
                    if (selectedIndex >= 0) {
                        Hero selectedHero = heroes.get(selectedIndex);
                        int newPrice = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the new price for " + selectedHero.getName() + ":"));
                        selectedHero.setPrice(newPrice);
                        updateHeroTable();
                        JOptionPane.showMessageDialog(null, "Hero price updated successfully!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Only admins can update hero prices!");
                }
            }
        });
        buttonsPanel.add(updateButton);
    }
    
    public void viewHistoryActionButton(JPanel buttonsPanel) {
    	historyButton = new JButton("History Transaction");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHistoryTransaction();
            }
        });
        buttonsPanel.add(historyButton);
    }
    
    public void buyHeroActionButton(JPanel buttonsPanel) {
    	if (!(currentUser instanceof Admin)) {
            buyButton = new JButton("Buy");
            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	int selectedIndex = heroTable.getSelectedRow();
                    Hero selectedHero = heroes.get(selectedIndex);
                    ((User) currentUser).addBoughtHero(selectedHero);
                    boughtHeroTableModel.setBoughtHeroes(((User) currentUser).getBoughtHeroes());
                    boughtHeroTableModel.fireTableDataChanged();
                    JOptionPane.showMessageDialog(null, "You bought " + selectedHero.getName() + " for " + selectedHero.getPrice() + " coins.");
                }
            });
            buttonsPanel.add(buyButton);
        }
    }
    
    public void logoutActionButton(JPanel buttonsPanel) {
    	logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = null;
                switchToLoginPanel();
                statusLabel.setText("");
            }
        });
        buttonsPanel.add(logoutButton);
    }

    public void buildCRUDPanel() {
    	crudPanel = new JPanel(new BorderLayout());

    	// Action Button
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        addHeroActionButton(buttonsPanel);
        removeHeroActionButton(buttonsPanel);
        updateHeroActionButton(buttonsPanel);
        viewHistoryActionButton(buttonsPanel);
        buyHeroActionButton(buttonsPanel);
        logoutActionButton(buttonsPanel);
        
        // Hero Table
        heroTableModel = new HeroTableModel(heroes);
        heroTable = new JTable(heroTableModel);

        JScrollPane tableScrollPane = new JScrollPane(heroTable);

        crudPanel.add(buttonsPanel, BorderLayout.NORTH);
        crudPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Status Label
        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);

        // Set initial panel
        switchToLoginPanel();
    }
    
    private void showHistoryTransaction() {
        List<Hero> historyHeroes = ((User) currentUser).getBoughtHeroes();
        HistoryTransactionDialog dialog = new HistoryTransactionDialog(historyHeroes);
        dialog.setVisible(true);
    }
    
    private void updatePanel(JPanel panel) {
    	getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    private void switchToLoginPanel() {
    	updatePanel(loginPanel);
    }

    private void switchToRegisterPanel() {
        updatePanel(registerPanel);
    }
    
    private void visibleButton(boolean checkAdd, boolean checkRemove, boolean checkUpdate, boolean checkBuy, boolean checkHistory) {
    	addButton.setVisible(checkAdd);
        removeButton.setVisible(checkRemove);
        updateButton.setVisible(checkUpdate);
        buyButton.setVisible(checkBuy);
        historyButton.setVisible(checkHistory);
    }
    
    private void switchToCRUDPanel() {
        updatePanel(crudPanel);
        if (currentUser instanceof Admin) {
        	visibleButton(true, true, true, false, false);
        } else {
        	visibleButton(false, false, false, true, true);
        }
    }

    private User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private void updateHeroTable() {
        heroTableModel.setHeroes(heroes);
        heroTableModel.fireTableDataChanged();
    }
    
    public MobailLejenApp() {
    	
        setFrame();
        initializeHero();
        initializeUser();

        boughtHeroTableModel = new BoughtHeroTableModel();
        
        // Login Panel
        buildLoginPanel();

        // Register Panel
        buildRegisterPanel();
        
        // CRUD Panel
        buildCRUDPanel();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MobailLejenApp app = new MobailLejenApp();
                app.setVisible(true);
            }
        });
    }
}
