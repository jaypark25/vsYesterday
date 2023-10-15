package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



// App to deal with the UI of the HabitTracker App!
public class HabitTrackerApp {
    static HabitList userList = new HabitList();
    static Scanner choice = new Scanner(System.in);
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/habitList.json";

    private JFrame habitTracker;


    //EFFECTS : sets up the HabitTrackerApp.
    public HabitTrackerApp() {
        setupScreen();

        //frameRecordDay();
        //frameEditMenu();
        //seeAnalytics();
        //quitApp();
        //loadAppButton();
        //saveAppButton();
    }

    //EFFECTS : Sets up physical setup screen.
    public void setupScreen() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        habitTracker = new JFrame();
        habitTracker.setSize(1280,720);
        habitTracker.setLocation(1920,0);
        habitTracker.setTitle("UI");
        habitTracker.setLayout(new GridLayout(4,2));
        JLabel recordDay = new JLabel("VSYesterday");
        recordDay.setBounds(600,10,100,30);
        habitTracker.add(recordDay);

        blackPink();
        //ImageIcon blackpink = new ImageIcon("data/BLACKPINKHE.jpeg");
        //JLabel test = new JLabel(blackpink);
        //habitTracker.add(test);
        //test.setVisible(true);
        setUpButtons();
        habitTracker.setVisible(true);
    }

    //EFFECTS : adds BLACKPINKimage
    private void blackPink() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("data/BLACKPINKHE.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(350, 220,
                Image.SCALE_SMOOTH);
        ImageIcon blackpink = new ImageIcon(dimg);
        JLabel test = new JLabel(blackpink);
        habitTracker.add(test);
        test.setVisible(true);
        test.setVisible(true);

    }

    //EFFFECTS : SETUP Main Frame
    public JFrame setUpFrame(String name) {
        JFrame frame = new JFrame();
        frame.setSize(1280,720);
        frame.setLocation(1920,0);
        frame.setTitle(name);
        frame.setVisible(true);
        return frame;
    }


    //EFFECTS : Setup new Buttons on UI.
    private void setUpButtons() {
        JButton recordDayButton = recordDayButton();
        habitTracker.add(recordDayButton);
        JButton editMenuButton = editMenuButton();
        habitTracker.add(editMenuButton);
        JButton seeStatsButton = seeStatsButton();
        habitTracker.add(seeStatsButton);
        JButton saveFileButton = saveFile();
        habitTracker.add(saveFileButton);
        JButton loadFileButton = loadFileButton();
        habitTracker.add(loadFileButton);
        JButton quitFileButton = quitButton();
        habitTracker.add(quitFileButton);
    }

    //EFFECTS: constructs and returns recordDayButton
    private JButton recordDayButton() {
        JButton recordDayButton = new JButton("Record Day");
        recordDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habitTracker.dispose();
                habitTracker = setUpFrame("RecordFrame");
                recordDayPanel();
            }
        });

        return recordDayButton;
    }

    //EFFECTS : creates and opens new recordDayPanel.
    private void recordDayPanel() {
        JPanel recordDayPanel = new JPanel(new CardLayout());
        habitTracker.add(recordDayPanel,  BorderLayout.CENTER);

        for (int i = 0; i < userList.getHabitList().size(); i++) {
            evaluateHabit(userList.getHabit(i), recordDayPanel);
        }
        //recordDayPanel.add(recordDay);
        //recordDayPanel.setVisible(true);
    }


    //EFFECTS : evaluates a habit.
    private void evaluateHabit(Habit h, JPanel topPanel) {
        JPanel currentPanel = new JPanel(new BorderLayout());
        JPanel currentVariable = new JPanel();
        currentPanel.add(currentVariable);
        currentPanel.setPreferredSize(new Dimension(400, 50));

        JPanel numberPanel = new JPanel(new FlowLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        if (h instanceof CheckHabit) {
            buttonPanel.add(completed((CheckHabit) h, currentPanel, topPanel));
            buttonPanel.add(failed((CheckHabit) h, currentPanel, topPanel));
            currentPanel.remove(currentVariable);
            currentVariable = buttonPanel;
            currentPanel.add(buttonPanel);
            buttonPanel.add(exitButton(buttonPanel));
        }
        if (h instanceof PersonalRecordHabit) {
            currentPanel.remove(currentVariable);
            currentVariable = numberPanel;
            numberPanel(numberPanel, h, topPanel);
            currentPanel.add(numberPanel);
        }

        currentPanel.add(new JLabel(h.getName()), BorderLayout.WEST);

        topPanel.add(currentPanel);
    }

    //EFFECTS : creates and works the number panel, where you add #rep.
    private void numberPanel(JPanel numberPanel, Habit h, JPanel topPanel) {
        JTextField insertNum = new JTextField("How many reps?");
        numberPanel.add(insertNum);
        JButton clickSubmit = new JButton("Submit");
        numberPanel.add(clickSubmit);
        numberPanel.add(exitButton(topPanel));
        clickSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String txt = insertNum.getText();
                int num = Integer.parseInt(txt);
                ((PersonalRecordHabit) h).addPR(num);
                CardLayout layout = (CardLayout) topPanel.getLayout();
                layout.next(topPanel);
            }
        });

    }

    //EFFECTS : creates exist Button.
    private JButton exitButton(JPanel currentPanel) {
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(exit);
                frame.dispose();
                setupScreen();
            }
        });
        return exit;
    }


    //EFFECTS : creates and returns button that marks if a habit is completed
    private JButton completed(CheckHabit h, JPanel current, JPanel topPanel) {
        JButton completed = new JButton("Completed");
        completed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.recordDay(true);
                CardLayout layout = (CardLayout) topPanel.getLayout();
                layout.next(topPanel);
            }
        });
        return completed;
    }

    //EFFECTS : creates and returns button that marks if a habit is failed
    private JButton failed(CheckHabit h, JPanel current, JPanel topPanel) {
        JButton failed = new JButton("Failed");
        failed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                h.recordDay(false);
                CardLayout layout = (CardLayout) topPanel.getLayout();
                layout.next(topPanel);
            }
        });
        return failed;
    }

    //MODIFIES : this
    //EFFECTS : constructs and returns edit menu button.
    private JButton editMenuButton() {
        JButton editMenuButton = new JButton("Edit Habits");
        editMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habitTracker.dispose();
                habitTracker = setUpFrame("EditFrame");
                editMenuPanel();
            }
        });

        return editMenuButton;
    }


    //EFFECTS : creates and opens new editMenuPanel
    private void editMenuPanel() {
        JPanel editMenuPanel = new JPanel();
        habitTracker.add(editMenuPanel);

        JButton createHabit = new JButton("Create a New Habit");
        editMenuPanel.add(createHabit);
        createHabit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenuPanel.setVisible(false);
                habitTracker.remove(editMenuPanel);
                createHabit();
            }
        });

        JButton removeHabit = new JButton("Remove a New Habit");
        editMenuPanel.add(removeHabit);
        removeHabit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editMenuPanel.setVisible(false);
                habitTracker.remove(editMenuPanel);
                removeHabit();
            }
        });
        editMenuPanel.setVisible(true);
    }

    //EFFECTS : removes habits.
    private void removeHabit() {
        JPanel removeJabit = new JPanel();
        habitTracker.add(removeJabit);
        JTextField removeName = new JTextField("Enter Name of Removed Habit Here.");
        JButton submit = new JButton("submit");
        removeJabit.add(submit);
        removeJabit.add(removeName, submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userList.getHabitList().size() == 0) {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(removeJabit);
                    frame.dispose();
                    setupScreen();
                }
                String text = removeName.getText();
                boolean yes = userList.removeHabit(text);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(removeJabit);
                frame.dispose();
                setupScreen();
            }
        });
    }

    //EFFECTS : creates new Habit.
    private void createHabit() {
        JPanel createHabitPanel = new JPanel();
        habitTracker.add(createHabitPanel);
        JTextField textField = new JTextField(20);
        createHabitPanel.add(textField);

        JButton checkList = createChecklistButton(textField);
        JButton prButton = countHabit(textField);
        createHabitPanel.add(checkList);
        createHabitPanel.add(prButton);
        habitTracker.add(createHabitPanel);

    }

    //EFFECTS : creates and returns checkListHabitButton
    private JButton createChecklistButton(JTextField tf) {
        JButton createCheckListHabit = new JButton("Create a Check-off Habit");
        createCheckListHabit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Habit checkHabit = new CheckHabit(tf.getText());
                userList.addHabit(checkHabit);
                habitTracker.dispose();
                setupScreen();
            }
        });

        return createCheckListHabit;
    }

    //EFFECTS : creates and returns countHabit
    private JButton countHabit(JTextField tf) {
        JButton createCheckListHabit = new JButton("Create a Counter Habit");
        createCheckListHabit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Habit prh = new PersonalRecordHabit(tf.getText());
                userList.addHabit(prh);
                habitTracker.dispose();
                setupScreen();
            }
        });
        return createCheckListHabit;
    }

    //EFFECTS : constructs and returns see stats button.
    private JButton seeStatsButton() {
        JButton seeStats = new JButton("View Stats");

        seeStats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                habitTracker.setVisible(false);
                habitTracker = setUpFrame("SeeStatsFrame");
                seeStatsMenu();
            }
        });

        return seeStats;
    }

    //EFFECTS : Write history of a habit
    public String addStatForHabit(Habit habit) {
        StringBuilder newSB = new StringBuilder();
        List<Boolean> history = habit.getHistory();
        int trueCount = 0;
        int historySize = history.size();
        for (int i = 0; i < historySize; i++) {
            String dayAgo = Integer.toString(i + 1);
            if (history.get(i)) {
                newSB.append("Day " + dayAgo + " [COMPLETED!]");
                trueCount += 1;
            } else {
                newSB.append("Day " + dayAgo + " [FAILED!]");
            }
        }
        String trueCountString = Integer.toString(trueCount);
        String historySizeString = Integer.toString(historySize);
        newSB.append("You have completed the habit " + trueCountString
                + " days out of " + historySizeString);
        return (newSB.toString());
    }

    //EFFECTS : Helper for PRStats
    private String prStats(Habit habit) {
        StringBuilder newSB = new StringBuilder();
        List<Integer> history = habit.getHistory();
        int highest = 0;
        if (history.size() == 0) {
            newSB.append("Zamn! No history!");

        } else {
            int lowest = history.get(0);
            int historySize = history.size();
            for (int i = 0; i < historySize; i++) {
                String dayAgo = Integer.toString(i + 1);
                int historyNow = history.get(i);
                newSB.append("Day " + dayAgo + ": " + historyNow + "reps!");
                if (historyNow > highest) {
                    highest = historyNow;
                }
                if (historyNow < lowest) {
                    lowest = historyNow;
                }
            }
            newSB.append("Your highest record is :" + highest);
            newSB.append("Your lowest record is :" + lowest);
        }
        return (newSB.toString());
    }

    //EFFFECTS : show Stats Menu
    private void seeStatsMenu() {
        JPanel statsMenu = new JPanel();
        BoxLayout boxLayout = new BoxLayout(statsMenu, BoxLayout.Y_AXIS);
        habitTracker.add(statsMenu);
        JLabel statsText = new JLabel();
        JButton exit = exitButton(statsMenu);
        statsMenu.add(exit);



        List<Habit> analytics = userList.getHabitList();
        if (userList.getHabitList().size() == 0) {
            statsText.setText("Sorry, no analytics because there are no habits.");
        }
        for (Habit h : analytics) {
            if (h instanceof CheckHabit) {
                JLabel text = new JLabel("Analytical screen for : " + h.getName());
                JLabel text2 = new JLabel(addStatForHabit(h));
                statsMenu.add(text);
                statsMenu.add(text2);
            } else if (h instanceof PersonalRecordHabit) {
                JLabel text = new JLabel("Analytical screen for : " + h.getName());
                JLabel text2 = new JLabel(prStats(h));
                statsMenu.add(text);
                statsMenu.add(text2);
            }
        }
    }

    //EFFECTS : constructs and returns save files button.
    private JButton saveFile() {
        JButton saveFileButton = new JButton("Save & Exit");
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveHabitList();
                JOptionPane.showMessageDialog(null, "File saved successfully.");
            }
        });
        return saveFileButton;
    }

    //EFFECTS : constructs and returns load file button.
    private JButton loadFileButton() {
        JButton loadFileButton = new JButton("Load Files");
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadHabitList();
                JOptionPane.showMessageDialog(null, "File loaded successfully.");
            }
        });
        return loadFileButton;
    }

    //EFFECTS : constructs and returns quit button
    private JButton quitButton() {
        JButton quitButton = new JButton("Quit Application");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printLog(EventLog.getInstance());
                habitTracker.dispose();
            }
        });
        return quitButton;
    }

    //EFFECTS : PRINT LOG
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.toString());
        }
    }


    //REQUIRES : user list must have > 0 habit.
    //EFFECTS : returns a stat page of current user habit list.
    public void getStatsCheck(Habit habit) {
        List<Boolean> history = habit.getHistory();
        int trueCount = 0;
        int historySize = history.size();
        for (int i = 0; i < historySize; i++) {
            String dayAgo = Integer.toString(i + 1);
            if (history.get(i)) {
                System.out.println("Day " + dayAgo + " [COMPLETED!]");
                trueCount += 1;
            } else {
                System.out.println("Day " + dayAgo + " [FAILED!]");
            }
        }
        String trueCountString = Integer.toString(trueCount);
        String historySizeString = Integer.toString(historySize);
        System.out.println("You have completed the habit " + trueCountString
                + " days out of " + historySizeString);
    }


    //EFFECTS : Given a Personal Record Habit, print statistics of each day.
    public void getPRCheck(Habit habit) {
        List<Integer> history = habit.getHistory();
        int highest = 0;
        if (history.size() == 0) {
            System.out.println("Zamn! No history!");

        } else {
            int lowest = history.get(0);
            int historySize = history.size();
            for (int i = 0; i < historySize; i++) {
                String dayAgo = Integer.toString(i + 1);
                int historyNow = history.get(i);
                System.out.println("Day " + dayAgo + ": " + historyNow + "reps!");
                if (historyNow > highest) {
                    highest = historyNow;
                }
                if (historyNow < lowest) {
                    lowest = historyNow;
                }
            }
            System.out.println("Your highest record is :" + highest);
            System.out.println("Your lowest record is :" + lowest);
        }
    }

    //MODIFIES : this
    //EFFECTS : ask user how many reps of habit they've completed, and returns it.
    public static int askNumReps(Habit h) {
        System.out.println("How much of " + h.getName() + " have you done today? (#)");
        String numRep = choice.nextLine();
        return Integer.parseInt(numRep);
    }

    //MODIFIES : this
    //EFFECTS : ask user whether they've completed the habit for the day, and returns it.
    public boolean askCompletedTask(Habit h) {
        System.out.println("Have you completed " + h.getName() + " today? Y/N");
        String yesOrNo = choice.nextLine();
        if (yesOrNo.equals("Y")) {
            return true;
        } else if (yesOrNo.equals("N")) {
            return false;
        } else {
            askCompletedTask(h);
        }
        askCompletedTask(h);
        return false;
    }

    //EFFECTS: checks if habit list size > 0 and sends user back to start screen; otherwise, record the day.
    private void recordMenu() {
        if (userList.getHabitList().size() == 0) {
            System.out.println("Sorry! you have no habits to update. Please add a new habit before you proceed!");
            startScreen();
        }
        recordDay();
    }

    //MODIFIES : this
    //REQUIRES : userList must have > 0 habits.
    //EFFECTS : records the user's day by going through every single habit.
    private void recordDay() {
        showHabitList();
        List<Habit> currentHabitList = userList.getHabitList();
        for (Habit h : currentHabitList) {
            if (h instanceof CheckHabit) {
                ((CheckHabit) h).recordDay(askCompletedTask(h));
                getStatsCheck(h);

            } else if (h instanceof PersonalRecordHabit) {
                ((PersonalRecordHabit) h).addPR(askNumReps(h));
                getPRCheck(h);
            }
        }
        System.out.println("Congratulations on recording your habits!");
        decideEnd();
    }

    //MODIFIES : this
    //EFFECTS : end program if user wish, otherwise go back to start screen.
    private void decideEnd() {
        System.out.println("Would you like to: ");
        System.out.println("1. Go to start screen.");
        System.out.println("2. End the program");
        String dec = choice.nextLine();
        switch (dec) {
            case "1":
                startScreen();
                break;
            case "2":
                System.out.println("ok, shutting down.");
                System.exit(1);
                break;
            default:
                System.out.println("None of the options chosen, going to start screen.");
                startScreen();
        }
    }

    //MODIFIES : this
    //EFFECTS : main menu for VSYesterday. Gives user the option to complete their user stories.
    private void startScreen() {
        System.out.println("Welcome to vsYesterday!");
        System.out.println("Which action would you like to perform?");
        System.out.println("1.Record today's habit process");
        System.out.println("2.Edit habits");
        System.out.println("3.See Analytical Screen");
        System.out.println("4.Quit Application");
        System.out.println("5. Load from a local file");
        System.out.println("6. Save current list");
        Scanner choice = new Scanner(System.in);
        String recordOrEdit = choice.nextLine();
        interactions(recordOrEdit);
    }

    //EFFECTS : deal with user interactions
    private void interactions(String choice) {
        if (choice.equals("1")) {
            recordMenu();
        } else if (choice.equals("2")) {
            editMenu();
        } else if (choice.equals("3")) {
            seeAnalytical();
        } else if (choice.equals("4")) {
            System.out.println("Shutting down...vsToday tomorrow!");
            System.exit(1);
        } else if (choice.equals("5")) {
            System.out.println("Reading from the past file....");
            loadHabitList();
            startScreen();
        } else if (choice.equals("6")) {
            System.out.println("Save to  file....");
            saveHabitList();
        } else {
            System.out.println("That is not a valid option.");
            startScreen();
        }

    }

    // EFFECTS: saves the workroom to file
    private void saveHabitList() {
        try {
            jsonWriter.open();
            jsonWriter.write(userList);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadHabitList() {
        try {
            userList = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    //EFFECTS : shows analytical screen for each habit in habitlist.
    private void seeAnalytical() {
        List<Habit> analytics = userList.getHabitList();
        if (userList.getHabitList().size() == 0) {
            System.out.println("Sorry, no analytics because there are no habits.");
            startScreen();
        }
        for (Habit h : analytics) {
            if (h instanceof CheckHabit) {
                System.out.println("Analytical scren for : " + h.getName());
                getStatsCheck(h);
            } else if (h instanceof PersonalRecordHabit) {
                System.out.println("Analytical scren for : " + h.getName());
                getPRCheck(h);
            }
        }
        startScreen();

    }

    //Modifies : this
    //EFFECTS : determine whether user would like to add / rename a habit, then redirect them to add / remove.
    private void editMenu() {
        System.out.println("How would you like to edit your Habit List?");
        System.out.println("1. Add a new Habit");
        System.out.println("2. Remove a habit");
        String whichEdit = choice.nextLine();
        switch (whichEdit) {
            case "1":
                selectAdd();
                break;
            case "2":
                selectRemove();
                break;
            default:
                System.out.println("Sorry, that is not a valid option.");
                editMenu();
        }
    }

    //MODIFIES : this
    //EFFECTS : remove wanted habit.
    public void selectRemove() {
        if (userList.getHabitList().size() == 0) {
            System.out.println("Sorry, your user list is empty currently :(");
            startScreen();
        }
        System.out.println("Here is your current list of Habits, which would you like to remove? (Write habit name)");
        showHabitList();
        String nameRemove = choice.nextLine();
        if (userList.removeHabit(nameRemove)) {
            System.out.println("Succesfully removed habit, here is your new list of habits.");
            startScreen();
        }
        System.out.println("There is no habit by that name");
        startScreen();

    }

    //MODIFIES : this
    //EFFECTS : add a new habit to user habit list.
    private void selectAdd() {
        System.out.println("What would you like to name your habit?");
        String nameHabit = choice.nextLine();
        String whichAdd = askHabitType();
        switch (whichAdd) {
            case "1":
                Habit newHabit = new CheckHabit(nameHabit);
                if (userList.addHabit(newHabit)) {
                    System.out.println("Congratulations! Your new habit has been added");
                    startScreen();
                    break;
                }
                System.out.println("Sorry, you already have a habit with this name.");
                selectAdd();
            case "2":
                newHabit = new PersonalRecordHabit(nameHabit);
                if (userList.addHabit(newHabit)) {
                    System.out.println("Congratulations! Your new habit has been added");
                    startScreen();
                    break;
                }
                System.out.println("Sorry, you already have a habit with this name.");
                selectAdd();
        }
    }

    //MODIFIES : this
    //EFFECTS: return which option user chosen when asked which habit type they'd like to add.
    private String askHabitType() {
        System.out.println("Which type of Habit would you like to add?");
        System.out.println("1. Checklist Habit, where you confirm if you've completed the task each day.");
        System.out.println("2. Personal Record Habit, where you record the #of reps / length of habit each day");
        return choice.nextLine();
    }


    //EFFECTS  show current list of habit.
    private void showHabitList() {
        List<Habit> currentHabitList = userList.getHabitList();
        System.out.println("Here is the current list of habits");
        int i = 1;
        for (Habit h : currentHabitList) {
            System.out.println(i + ". " + h.getName());
            i++;
        }

    }

    private JComboBox<String> printCombo;
}
