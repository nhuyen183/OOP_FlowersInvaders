import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StateQuit implements State, ActionListener{

    private JPanel quitPanel;
    private Board board;

    public StateQuit(Board board){
        this.board = board;

        quitPanel = new JPanel();
        quitPanel.setLayout(new BoxLayout(quitPanel, BoxLayout.PAGE_AXIS));

        if (JOptionPane.showConfirmDialog(quitPanel,"Do you want to quit","Flowers Invaders Game",
                JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            System.exit(0);

        quitPanel.add(Box.createRigidArea(Constants.QUIT_BUTTON_PADDING));

        JButton returnButton = new JButton();
        returnButton.setActionCommand(Strings.ACTION_RETURN);
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setMaximumSize(Constants.BUTTON_RETURN_SIZE);
        returnButton.setText(Strings.BUTTON_RETURN);
        returnButton.setFont(Constants.FONT_BUTTON);
        returnButton.setVisible(true);
        quitPanel.add(returnButton);

        returnButton.addActionListener(this);
    }

    public JPanel getMainPanel(){
        return quitPanel;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case Strings.ACTION_RETURN:
                board.setCurrentState(new StateMenu(board));
        }
    }
}
