import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: User1
 * Date: 4/30/12
 * Time: 1:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class mWindow {

    public static void main(String[] args) {
        GridLayout layout=new GridLayout(4,2);
        final JFrame frame = new JFrame("ClearMail-DT");
        frame.setLayout(layout);
        final JButton goB = new JButton("Go!");

        JLabel sl = new JLabel("Server: ");
        JLabel ul = new JLabel("Username: ");
        JLabel pl = new JLabel("Password: ");
        final JTextField st = new JTextField("",20);
        final JTextField ut = new JTextField("",20);
        final JPasswordField pt = new JPasswordField("",20);
        frame.add(sl);
        frame.add(st);
        frame.add(ul);
        frame.add(ut);
        frame.add(pl);
        frame.add(pt);
        frame.add(goB);
        frame.setSize(300,110);
        goB.addActionListener(new ActionListener() {
            public int num;

            @Override
            public void actionPerformed(ActionEvent e) {
                String server = st.getText();
                String user = ut.getText();
                String pass = pt.getText();

               
                try {
                    num = go(server, user, pass);
                } catch (MessagingException e1) {
                    JOptionPane.showMessageDialog(frame, e1.toString() + "  >>>  Error occurred.");
                   // System.exit(2);
                }
                    if (num < 1){
                        JOptionPane.showMessageDialog(frame, "No messages marked as read");
                    }  else {

                    JOptionPane.showMessageDialog(frame, "Successfully marked " + num + " messages as read.");
                    }

             
                //JOptionPane.showMessageDialog(frame, "Successfully marked " + num + " messages as read.");
            }
        });
        frame.setVisible(true);
        // go(server,user,pass);
    }


    public static int go(String server, String user, String pass) throws MessagingException {

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");
        int unread;


            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(server, user, pass);
           // System.out.println(store);

            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_WRITE);
            unread = inbox.getUnreadMessageCount();

            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
            Message messages[] = inbox.search(ft);


            for (Message message : messages) {

                message.setFlag(Flags.Flag.SEEN, true);
            }


        return unread;

    }
}
