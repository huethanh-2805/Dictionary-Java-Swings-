/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Word;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MainScreen extends javax.swing.JFrame {

    private final File anhVietFile = new File("data/Anh_Viet.xml");
    private final File vietAnhFile = new File("data/Viet_Anh.xml");

    boolean isEnglish = true;

    private DefaultListModel<String> wordListModel;

    //Khai báo biến để lưu trữ
    private List<Word> anhVietDictionary;
    private List<Word> vietAnhDictionary;
    private List<Word> myFavoriteList;

    public MainScreen() {
        initComponents();
        init();
        loadData(); //Load dữ liệu từ file xml
        insertData(); //Đưa dữ liệu vào List hiển thị
        loadFavoriteList();
    }

    private void init() {
        anhVietDictionary = new ArrayList<>();
        vietAnhDictionary = new ArrayList<>();
        myFavoriteList = new ArrayList<>();

        setTitle("Từ điển Anh-Việt/Việt-Anh");
        Color customLightBlue = new Color(204, 255, 255); // Màu xanh nhạt tùy chỉnh
        getContentPane().setBackground(customLightBlue);
        this.jPanel1.setBackground(Color.blue);
    }

    private void loadData() {
        File filePath1 = anhVietFile;
        File filePath2 = vietAnhFile;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // Tạo một DocumentBuilder để phân tích file XML
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Phân tích file XML
            Document doc = builder.parse(filePath1);
            doc.getDocumentElement().normalize();

            // Lấy danh sách các thẻ <record>
            NodeList recordList = doc.getElementsByTagName("record");

            // Duyệt qua từng thẻ <record>
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;

                    // Lấy nội dung của thẻ <word> và <meaning> đọc từ file xml
                    String word = recordElement.getElementsByTagName("word").item(0).getTextContent();
                    String meaning = recordElement.getElementsByTagName("meaning").item(0).getTextContent();
                    Word newWord = new Word(word, meaning);

                    //Lưu vào danh sách từ điển
                    anhVietDictionary.add(newWord);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }

        // Xử lý từ điển Việt-Anh
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath2);
            doc.getDocumentElement().normalize();

            // Lấy danh sách các thẻ <record>
            NodeList recordList = doc.getElementsByTagName("record");

            // Duyệt qua từng thẻ <record>
            for (int i = 0; i < recordList.getLength(); i++) {
                Node recordNode = recordList.item(i);
                if (recordNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element recordElement = (Element) recordNode;

                    // Lấy nội dung của thẻ <word> và <meaning> đọc từ file xml
                    String word = recordElement.getElementsByTagName("word").item(0).getTextContent();
                    String meaning = recordElement.getElementsByTagName("meaning").item(0).getTextContent();
                    Word newWord = new Word(word, meaning);

                    //Lưu vào danh sách từ điển Việt-Anh
                    vietAnhDictionary.add(newWord);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }

        loadAddList();
        loadDeleteList();
    }

    private void insertData() {
        wordListModel = new DefaultListModel<>();
        List<Word> sourceDictionary = isEnglish ? anhVietDictionary : vietAnhDictionary;

        for (Word word : sourceDictionary) {
            wordListModel.addElement(word.getWord());
        }
        wordList.setModel(wordListModel);
    }

    private void loadFavoriteList() {
        String fileName = "data/favoriteWordList.txt";

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = findMeaning(word);
                    Word favoriteWord = new Word(word, meaning);
                    myFavoriteList.add(favoriteWord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAddList() {
        String fileName1 = "data/anh_viet_dictionary.txt";
        String fileName2 = "data/viet_anh_dictionary.txt";

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName1))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();
                    Word newWord = new Word(word, meaning);
                    anhVietDictionary.add(newWord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String word = parts[0].trim();
                    String meaning = parts[1].trim();
                    Word newWord = new Word(word, meaning);
                    vietAnhDictionary.add(newWord);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDeleteList() {
        String fileName = "data/deleteWord.txt";
        try ( BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                deleteWord(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteWord(String wordToDelete) {
        for (Word word : anhVietDictionary) {
            if (word.getWord().equals(wordToDelete)) {
                anhVietDictionary.remove(word);
                break;
            }
        }

        for (Word word : vietAnhDictionary) {
            if (word.getWord().equals(wordToDelete)) {
                vietAnhDictionary.remove(word);
                break;
            }
        }
    }

    private String findMeaning(String word) {
        List<Word> sourceDictionary = isEnglish ? anhVietDictionary : vietAnhDictionary;

        // Duyệt qua từng từ trong danh sách từ điển
        for (Word dictWord : sourceDictionary) {
            if (dictWord.getWord().equals(word)) {
                return dictWord.getMeaning();
            }
        }

        return ""; // Trả về chuỗi rỗng nếu không tìm thấy nghĩa của từ
    }

    private void displayMeaning(String selectedWord, String meaning) {
        StringBuilder formattedTextBuilder = new StringBuilder();
        formattedTextBuilder.append("<html><body>");

        // Thêm từ và nghĩa vào formattedTextBuilder
        formattedTextBuilder.append("<font color='red'>Word</font>: <font color='blue'>")
                .append(selectedWord)
                .append("</font><br>")
                .append("<font color='red'>Meaning</font>: <font color='blue'>")
                .append("<ul style='list-style-type: none; padding-left: 0;'>");

        // Tách nghĩa thành các dòng, mỗi dòng bắt đầu bằng "-" và thêm vào formattedTextBuilder
        String[] meaningLines = meaning.split("\n");
        for (String line : meaningLines) {
            formattedTextBuilder.append("<li>").append(line.trim()).append("</li>");
        }

        formattedTextBuilder.append("</ul>")
                .append("</font>");

        meaningTextPane.setContentType("text/html");
        meaningTextPane.setText(formattedTextBuilder.toString());
    }

    private void searchDictionary(String query) {
        DefaultListModel<String> filteredListModel = new DefaultListModel<>();

        // Duyệt qua danh sách từ điển để tìm kiếm từ khớp với query
        List<Word> sourceDictionary = isEnglish ? anhVietDictionary : vietAnhDictionary;
        for (Word word : sourceDictionary) {
            if (word.getWord().toLowerCase().contains(query.toLowerCase())) {
                filteredListModel.addElement(word.getWord());
            }
        }

        // Hiển thị danh sách kết quả tìm kiếm
        wordList.setModel(filteredListModel);
    }

    //Kiểm tra từ đã lưu trong danh sách ưa thích chưa
    private boolean isWordAlreadySaved(String word) {
        for (Word favoriteWord : myFavoriteList) {
            if (favoriteWord.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }

    //Lưu vào danh sách yêu thích
    private void saveFavoriteList() {
        String fileName = "data/favoriteWordList.txt";

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Word favoriteWord : myFavoriteList) {
                writer.write(favoriteWord.getWord() + ": " + favoriteWord.getMeaning());
                writer.newLine();
            }
            // Display success message after saving the list
            JOptionPane.showMessageDialog(this, "Danh sách từ yêu thích đã được lưu thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lưu danh sách từ yêu thích.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeHistory(String selectedWord) {
        String fileName = "data/history.txt";
        Path filePath = Paths.get(fileName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String currentDate = LocalDate.now().format(formatter);

        try {
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(selectedWord + " - " + currentDate);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titleProgram = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchInput = new javax.swing.JTextField();
        selectedLanguage = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        wordList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        meaningTextPane = new javax.swing.JTextPane();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnMyFavorite = new javax.swing.JButton();
        btnStatistics = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAddFavorite = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        newWordText = new javax.swing.JTextField();
        meaningNewText = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(153, 255, 255));

        titleProgram.setBackground(new java.awt.Color(153, 153, 255));
        titleProgram.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        titleProgram.setForeground(new java.awt.Color(255, 255, 0));
        titleProgram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleProgram.setText("DIRECTIONARY SYSTEM");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleProgram, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleProgram, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Find word");

        searchInput.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        searchInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        searchInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchInputActionPerformed(evt);
            }
        });
        searchInput.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchInputKeyReleased(evt);
            }
        });

        selectedLanguage.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        selectedLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Anh-Việt", "Việt-Anh" }));
        selectedLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectedLanguageActionPerformed(evt);
            }
        });

        wordList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        wordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wordListMouseClicked(evt);
            }
        });
        wordList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                wordListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(wordList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(searchInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectedLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectedLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));

        meaningTextPane.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jScrollPane2.setViewportView(meaningTextPane);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Meaning");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 388, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        btnMyFavorite.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMyFavorite.setText("My favorite words");
        btnMyFavorite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMyFavoriteActionPerformed(evt);
            }
        });

        btnStatistics.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnStatistics.setText("Statistics");
        btnStatistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStatisticsActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAdd.setText("Add ");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAddFavorite.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnAddFavorite.setText("Add to favorites");
        btnAddFavorite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFavoriteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Meaning:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Add new words:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Word:");

        newWordText.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        newWordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newWordTextActionPerformed(evt);
            }
        });

        meaningNewText.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        meaningNewText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meaningNewTextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAddFavorite)
                        .addGap(79, 79, 79)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnMyFavorite)
                        .addGap(54, 54, 54)
                        .addComponent(btnStatistics)
                        .addGap(65, 65, 65))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnAdd)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel5))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(meaningNewText)
                                        .addComponent(newWordText, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddFavorite)
                    .addComponent(btnDelete)
                    .addComponent(btnMyFavorite)
                    .addComponent(btnStatistics))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(newWordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(meaningNewText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAdd)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchInputActionPerformed

    private void btnMyFavoriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMyFavoriteActionPerformed
        // TODO add your handling code here:
        MyFavoriteScreen mfs = new MyFavoriteScreen();
        mfs.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mfs.setMyFavoriteList(myFavoriteList); // Truyền danh sách yêu thích vào MyFavoriteScreen
        mfs.setLocationRelativeTo(null);
        mfs.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mfs.dispose();
            }
        });
        mfs.show();
    }//GEN-LAST:event_btnMyFavoriteActionPerformed

    private void selectedLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectedLanguageActionPerformed
        // TODO add your handling code here:
        String language = selectedLanguage.getSelectedItem().toString();
        if (language.equals("Anh-Việt")) {
            isEnglish = true;
            meaningTextPane.setText("");
        } else {
            isEnglish = false;
            meaningTextPane.setText("");
        }

        insertData();
    }//GEN-LAST:event_selectedLanguageActionPerformed

    private void btnStatisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStatisticsActionPerformed
        // TODO add your handling code here:
        StatisticsScreen s = new StatisticsScreen();
        s.setLocationRelativeTo(null);
        s.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        s.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                s.dispose();
            }
        });
        s.setVisible(true);
    }//GEN-LAST:event_btnStatisticsActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (newWordText.getText().equals("") || meaningNewText.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa điền dữ liệu đầy đủ", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newWord = newWordText.getText();
        String meaning = meaningNewText.getText();

        String fileName;
        String language = selectedLanguage.getSelectedItem().toString();
        if (language.equals("Anh-Việt")) {
            fileName = "data/anh_viet_dictionary.txt";
        } else {
            fileName = "data/viet_anh_dictionary.txt";
        }

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            // Ghi từ mới và nghĩa vào file
            writer.write(newWord + ": " + meaning);
            writer.newLine();

            // Hiển thị thông báo khi lưu thành công
            JOptionPane.showMessageDialog(this, "Từ mới đã được lưu vào file.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Xóa nội dung trên các trường nhập liệu sau khi lưu
            newWordText.setText("");
            meaningNewText.setText("");
        } catch (IOException e) {
            // Xử lý ngoại lệ nếu gặp lỗi khi ghi file
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lưu từ mới.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        loadData();
        insertData();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        String deletedWord = wordList.getSelectedValue();

        if (deletedWord == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn từ nào để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try ( FileWriter writer = new FileWriter("data/deleteWord.txt", true);  BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(deletedWord + "\n");
            JOptionPane.showMessageDialog(this, "Từ '" + deletedWord + "' đã được xóa và lưu vào file 'deleteWord.txt'.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi khi lưu từ đã xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        loadData();
        insertData();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddFavoriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFavoriteActionPerformed
        // TODO add your handling code here:
        int selectedIndex = wordList.getSelectedIndex();

        if (selectedIndex == -1) {
            // Hiển thị thông báo nếu không có từ nào được chọn
            JOptionPane.showMessageDialog(this, "Vui lòng chọn từ trước khi thêm vào danh sách yêu thích.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedWord = wordListModel.getElementAt(selectedIndex);

        // Kiểm tra xem từ đã được lưu hay chưa
        if (isWordAlreadySaved(selectedWord)) {
            JOptionPane.showMessageDialog(this, "Từ đã được lưu trong danh sách yêu thích.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            String meaning = findMeaning(selectedWord);
            Word newFavoriteWord = new Word(selectedWord, meaning);
            myFavoriteList.add(newFavoriteWord);
            saveFavoriteList();
        }

    }//GEN-LAST:event_btnAddFavoriteActionPerformed

    private void wordListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_wordListValueChanged
        // TODO add your handling code here:

    }//GEN-LAST:event_wordListValueChanged

    private void wordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wordListMouseClicked
        // TODO add your handling code here:
        // Lấy từ được chọn
        String selectedWord = wordList.getSelectedValue();

        if (selectedWord != null) {
            // Tìm nghĩa của từ được chọn
            String meaning = findMeaning(selectedWord);

            // Hiển thị nghĩa của từ
            displayMeaning(selectedWord, meaning);

            // Lưu lại lịch sử tra từ
            writeHistory(selectedWord);
        }
    }//GEN-LAST:event_wordListMouseClicked

    private void searchInputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchInputKeyReleased
        // TODO add your handling code here:
        String query = searchInput.getText().trim();

        if (query.isEmpty()) {
            // Nếu trường tìm kiếm trống, hiển thị toàn bộ từ điển
            insertData();
        } else {
            // Ngược lại, thực hiện tìm kiếm
            searchDictionary(query);
        }
    }//GEN-LAST:event_searchInputKeyReleased

    private void newWordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newWordTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_newWordTextActionPerformed

    private void meaningNewTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meaningNewTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_meaningNewTextActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddFavorite;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnMyFavorite;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField meaningNewText;
    private javax.swing.JTextPane meaningTextPane;
    private javax.swing.JTextField newWordText;
    private javax.swing.JTextField searchInput;
    private javax.swing.JComboBox<String> selectedLanguage;
    private javax.swing.JLabel titleProgram;
    private javax.swing.JList<String> wordList;
    // End of variables declaration//GEN-END:variables
}
