import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StateWin implements State, ActionListener {
    private JPanel winPanel;
    private Board board;

    public StateWin(Board board){
        this.board = board;

        winPanel = new JPanel();
        winPanel.setLayout(new BoxLayout(winPanel, BoxLayout.PAGE_AXIS));

        JLabel header = new JLabel();
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setMaximumSize(Constants.HEADER_SIZE);
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setText(Strings.WIN_HEADER);
        header.setFont(Constants.FONT_HEADER);
        header.setVisible(true);
        winPanel.add(header);

        winPanel.add(Box.createRigidArea(Constants.HEADER_PADDING));

        winPanel.add(Box.createRigidArea(Constants.QUIT_BUTTON_PADDING));

        JButton returnButton = new JButton();
        returnButton.setActionCommand(Strings.ACTION_RETURN);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setMaximumSize(Constants.BUTTON_RETURN_SIZE);
        returnButton.setText(Strings.BUTTON_RETURN);
        returnButton.setFont(Constants.FONT_BUTTON);
        returnButton.setVisible(true);
        winPanel.add(returnButton);

        returnButton.addActionListener(this);
    }

    public JPanel getMainPanel(){
        return winPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case Strings.ACTION_RETURN:
                board.setCurrentState(new StateMenu(board));
        }
    }
}
