package main;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.DefaultListModel;
import model.Word;

public class MyFavoriteScreen extends javax.swing.JFrame {

    private List<Word> favoriteList;
    private DefaultListModel<String> wordListModel;

    public MyFavoriteScreen() {
        initComponents();
        init();
    }

    private void init() {
        Color customLightBlue = new Color(204, 255, 255);
        getContentPane().setBackground(customLightBlue);
        this.jPanel1.setBackground(Color.blue);
    }

    public void setMyFavoriteList(List<Word> myFavoriteList) {
        this.favoriteList = myFavoriteList;
        displayList();
    }

    private void displayList() {
        wordListModel = new DefaultListModel<>();

        for (Word word : favoriteList) {
            wordListModel.addElement(word.getWord());
        }
        wordFavoriteList.setModel(wordListModel);
    }

    private String findMeaning(String word) {
        // Duyệt qua từng từ trong danh sách từ điển
        for (Word dictWord : favoriteList) {
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

        meaningFavoriteText.setContentType("text/html");
        meaningFavoriteText.setText(formattedTextBuilder.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        titleProgram = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sortWord = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        wordFavoriteList = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        meaningFavoriteText = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        titleProgram.setBackground(new java.awt.Color(153, 153, 255));
        titleProgram.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        titleProgram.setForeground(new java.awt.Color(255, 255, 0));
        titleProgram.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleProgram.setText("My Favorite");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleProgram, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleProgram, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Words");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Sort by:");

        sortWord.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        sortWord.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A-Z", "Z-A" }));
        sortWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortWordActionPerformed(evt);
            }
        });

        wordFavoriteList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wordFavoriteListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(wordFavoriteList);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Meaning");

        jScrollPane1.setViewportView(meaningFavoriteText);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(99, 99, 99)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(sortWord, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sortWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortWordActionPerformed
        // TODO add your handling code here:\
        String sort = sortWord.getSelectedItem().toString();
        
        if (sort.equals("A-Z")) {
            Collections.sort(favoriteList, Comparator.comparing(Word::getWord));
        } else {
            Collections.sort(favoriteList, Comparator.comparing(Word::getWord).reversed());
        }

        displayList();
    }//GEN-LAST:event_sortWordActionPerformed

    private void wordFavoriteListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wordFavoriteListMouseClicked
        // TODO add your handling code here:
        //Lấy vị trí được chọn
        int selectedIndex = wordFavoriteList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedWord = wordListModel.getElementAt(selectedIndex);
            String meaning = findMeaning(selectedWord); //Tìm nghĩa của từ đó

            // Gọi hàm để hiển thị nghĩa của từ
            displayMeaning(selectedWord, meaning);
        }
    }//GEN-LAST:event_wordFavoriteListMouseClicked

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
            java.util.logging.Logger.getLogger(MyFavoriteScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyFavoriteScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyFavoriteScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyFavoriteScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MyFavoriteScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane meaningFavoriteText;
    private javax.swing.JComboBox<String> sortWord;
    private javax.swing.JLabel titleProgram;
    private javax.swing.JList<String> wordFavoriteList;
    // End of variables declaration//GEN-END:variables

}
