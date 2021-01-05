import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StateScoresBoard implements State, ActionListener {

    private JPanel scoresBoardPanel;
    private Board board;

    public StateScoresBoard(Board board) {
        this.board = board;

        scoresBoardPanel = new JPanel();
        scoresBoardPanel.setLayout(new BoxLayout(scoresBoardPanel, BoxLayout.PAGE_AXIS));

        //About text label
        JLabel header = new JLabel();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setMaximumSize(Constants.HEADER_SIZE);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setText(Strings.HIGH_SCORES_HEADER);
        header.setFont(Constants.FONT_HEADER);
        header.setVisible(true);
        scoresBoardPanel.add(header);

        //margin
        scoresBoardPanel.add(Box.createRigidArea(Constants.HEADER_PADDING));

        //Text
        JLabel aboutText = new JLabel();
        aboutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutText.setMaximumSize(Constants.HIGH_SCORES_TEXT_BOX);
        aboutText.setHorizontalAlignment(SwingConstants.CENTER);
        aboutText.setText(Strings.HIGH_SCORES_TEXT);
        aboutText.setFont(Constants.FONT_TEXT);
        aboutText.setVisible(true);
        scoresBoardPanel.add(aboutText);

        //Margin between buttons
        scoresBoardPanel.add(Box.createRigidArea(Constants.RETURN_BUTTON_PADDING));

        //Return to main menu button
        JButton returnButton = new JButton();
        returnButton.setActionCommand(Strings.ACTION_RETURN);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setMaximumSize(Constants.BUTTON_RETURN_SIZE);
        returnButton.setText(Strings.BUTTON_RETURN);
        returnButton.setFont(Constants.FONT_BUTTON);
        returnButton.setVisible(true);
        scoresBoardPanel.add(returnButton);

        returnButton.addActionListener(this);
    }

    public JPanel getMainPanel(){
        return scoresBoardPanel;
    }

    //@Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case Strings.ACTION_RETURN:
                board.setCurrentState(new StateMenu(board));
                break;
            default:
               break;
      }
   }
}
