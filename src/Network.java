import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Network {

    final private Array<Profile> profiles;
    final private Scanner scan;
    Graph<String> profilesIdGraph;
    
    public Network() {
        profiles = new Array<>();
        profilesIdGraph = new Graph<>();
        scan = new Scanner(System.in);
    }

    private void displayMenu() {
        System.out.println("1. Join the network.\n"
                + "2. Leave the network.\n"
                + "3. Modify the profile.\n"
                + "4. Search for other profile.\n"
                + "5. Add a friend.\n"
                + "6. Delete a friend.\n"
                + "7. Update Status.\n"
                + "8. See friend of friends.\n"
                + "9. Quit.\n");
    }

    private int inputInteger() {
        do {
            try {
                int val = Integer.parseInt(scan.nextLine());
                return val;
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer.");
            }
        } while (true);
    }

    private int isIdAvailable(String id) {
        Profile p = new Profile(id, "");
        return profiles.findElement(p);
    }

    private Profile get(int index) {
        return profiles.getElement(index);
    }

    
    private void joinNetwork() {
        String id;
        System.out.println("Enter id: ");
        int check;
        do {
            id = scan.nextLine();
            check = isIdAvailable(id);
            if (check != -1) {
                System.out.println("This id is already taken. Please try different id.");
            }
        } while (check != -1);
        System.out.println("Enter name: ");
        String name = scan.nextLine();
        profiles.addElement(new Profile(id, name));
        profilesIdGraph.addVertex(id);
        System.out.println("congratulation!! You are on the network.");
    }

    private void leaveNetwork() {
        System.out.println("Enter id: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }
        profiles.removeElement(index);
        profilesIdGraph.removeVertex(id);
        System.out.println("Sorry to see you leaving!! You are now not on the network.");
    }


    private void modifyProfile() {
        System.out.println("Enter previous id: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }

        Profile prev = get(index);
        profiles.removeElement(index);

        System.out.println("Enter new name: ");
        String name = scan.nextLine();
        profiles.addElement(new Profile(prev.getId(), name, prev.isStatus()));
        System.out.println("Your profile is updated");
    }

    private void seeFriendOfFriends() {
        System.out.println("Enter id to search: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        int frindIndex;
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }
        System.out.println("\tYour friends:");
        
        for(String elem:profilesIdGraph.getEdges(id)){
            //elem is a friend id
            frindIndex = profiles.findElement(new Profile(elem, ""));
            System.out.println("\t\t"+profiles.getElement(frindIndex).getName()+
                    " friends:");
            for(String e:profilesIdGraph.getEdges(elem)){
                frindIndex = profiles.findElement(new Profile(e, ""));
                System.out.println("\t\t\t"+profiles.getElement(frindIndex).getName());
            }
        }
    
    }
    
    private void searchForProfiles() {
        System.out.println("Enter id to search: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
        } else {
            System.out.println("Profile found: ");
            System.out.println(get(index));
            for(String elem:profilesIdGraph.getEdges(id)){
                System.out.println("\t\t"+
                        profiles.getElement(
                                profiles.findElement(new Profile(elem, "")))
                                .getName());
            }
        }
    }

    private void addFriend() {
        System.out.println("Enter your id: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }
        System.out.println("Enter your friend's id: ");
        String friendsId = scan.nextLine();
        int friendIdIndex = isIdAvailable(friendsId);
        if (friendIdIndex == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }

        profilesIdGraph.addEdge(id, friendsId);
        profilesIdGraph.addEdge(friendsId, id);

    }
    
    private void deleteFriend() {
        System.out.println("Enter your id: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }
        System.out.println("Enter your friend's id: ");
        String friendsId = scan.nextLine();
        int friendIdIndex = isIdAvailable(friendsId);
        
        if (friendIdIndex == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }

        profilesIdGraph.removeEdge(id,friendsId);
        profilesIdGraph.removeEdge(friendsId,id);
    }


    private void updateStatus() {
        System.out.println("Enter your id: ");
        String id = scan.nextLine();
        int index = isIdAvailable(id);
        if (index == -1) {
            System.out.println("This id doesnt exists.");
            return;
        }
        System.out.println("Your current status is " + (get(index).isStatus() ? "Online" : "Offline"));
        System.out.println("Do you want to change it? (Yes/Y)");
        String option = scan.nextLine();
        if (option.equalsIgnoreCase("yes") || option.equalsIgnoreCase("y")) {
            get(index).setStatus(!get(index).isStatus());
            System.out.println("Your new status is " + (get(index).isStatus() ? "Online" : "Offline"));
        }
    }

    private static void writeToFile(Network nw) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(new File("profiles.dat")));
            Profile p;
            
            for(int i = 0; i< nw.profiles.getSize();i++) {
                p = nw.profiles.getElement(i);
                osw.write(p.getId() + "," + p.getName() + "," + (p.isStatus() ? "true" : "false"));
                for (String f : nw.profilesIdGraph.getEdges(p.getId())) {
                    osw.write("," + f);
                }
                osw.write("\n");
            }
            osw.close();
        } catch (IOException ex) {
            Logger.getLogger(Network.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void readProfiles(Network net) {
        File f = new File("profiles.dat");
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            return;
        }
        String line;
        String[] splits;
        Profile p;
        List<String> friendsList = new List<>();
        List<Integer> LengthList = new List<>();

        while (s.hasNextLine()) {
            line = s.nextLine();
            splits = line.split(",");
            p = new Profile(splits[0], splits[1], splits[2].equals("true"));
            LengthList.add(splits.length - 3);
            for (int i = 3; i < splits.length; i++) {
                friendsList.add(splits[i]);
            }
            net.profiles.addElement(p);
            net.profilesIdGraph.addVertex(p.getId());
        }
        Iterator<String> ss = friendsList.iterator();
        Iterator<Integer> ll = LengthList.iterator();
        int size;
        for(int i =0;i<net.profiles.getSize();i++){
            p=net.profiles.getElement(i);
            size = ll.next();
            for (int j = 0; j < size; j++) {
                net.profilesIdGraph.addEdge(p.getId(), ss.next());
            }
        }
    }

    public static void main(String[] args) {
        Network network = new Network();
        int option;
        readProfiles(network);
        do {
            network.displayMenu();
            option = network.inputInteger();
            switch (option) {
                case 1:
                    network.joinNetwork();
                    break;
                case 2:
                    network.leaveNetwork();
                    break;
                case 3:
                    network.modifyProfile();
                    break;
                case 4:
                    network.searchForProfiles();
                    break;
                case 5:
                    network.addFriend();
                    break;
                case 6:
                    network.deleteFriend();
                    break;
                case 7:
                    network.updateStatus();
                    break;
                case 8:
                    network.seeFriendOfFriends();
                    break;
                
                case 9:
                    System.out.println("Thanks for using the system.");
                    writeToFile(network);
                    break;
            }
        } while (option != 9);
    }

    
}