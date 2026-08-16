package structure;
import model.Train;
import java.util.ArrayList;
import java.util.List;

public class TrainLinkedList {

    TrainNode head;
    private int size;
    // Node in the chain
    static class TrainNode {


        Train train;      // the cargo
        TrainNode next; // link to the next node

        public TrainNode(Train train) {
            this.train = train;
            this.next = null;
        }

    }


    // Add train

    public boolean addTrain(Train train){
        if(train == null){
            return false;
        }
        //checks for duplicates train id

        if(searchTrainById(train.getTrainId())!=null){
            System.out.println("Train ID already exists");
            return false;
        }

        TrainNode newNode = new TrainNode(train);

        //first train
        if(head == null){
            head = newNode;

        }
        else {
            TrainNode current = head;
            while(current.next != null){
                current = current.next;
            }

            // add new node to end
            current.next = newNode;
        }
        size++;
        return true;
    }



    // Linear search by train ID

    public Train searchTrainById(String trainId) {
        if (trainId == null || trainId.trim().isEmpty()) return null;
        trainId = trainId.trim();
        TrainNode current = head;
        while (current != null) {

            if (current.train.getTrainId().equalsIgnoreCase(trainId)) {
                return current.train;
            }
            current = current.next;
        }
        return null;
    }


    //Search trains by starting station and destination

    public void searchTrainByRoute(String start, String destination) {
        if (start == null || destination == null) {
            System.out.println("No trains found.");
            return;
        }
        TrainNode current = head;
        boolean found = false;
        while (current != null) {
            Train train = current.train;

            if (train.getStartStation().equalsIgnoreCase(start)
                    && train.getDestination().equalsIgnoreCase(destination)) {

                train.displayTrainInfo();
                System.out.println("====================");
                found = true;
            }
            current = current.next;
        }

        if (!found) {
            System.out.println("No trains found.");
        }
    }

    // Return matching trains for use by the interactive menu.
    public List<Train> findTrainsByRoute(String start, String destination) {
        List<Train> matches = new ArrayList<Train>();
        if (start == null || destination == null) return matches;

        TrainNode current = head;
        while (current != null) {
            Train train = current.train;
            if (train.getStartStation().equalsIgnoreCase(start.trim())
                    && train.getDestination().equalsIgnoreCase(destination.trim())) {
                matches.add(train);
            }
            current = current.next;
        }
        return matches;
    }

    // Return all trains without exposing linked-list nodes.
    public List<Train> getAllTrains() {
        List<Train> result = new ArrayList<Train>();
        TrainNode current = head;
        while (current != null) {
            result.add(current.train);
            current = current.next;
        }
        return result;
    }


    //Display all trains

    public void displayTrains() {

        if (head == null) {
            System.out.println("No trains stored.");
            return;
        }
        TrainNode current = head;
        while (current != null) {
            current.train.displayTrainInfo();
            System.out.println("====================");
            current = current.next;
        }
    }

    //delete trains
    public boolean deleteTrain(String trainId) {
        if (head == null || trainId == null || trainId.trim().isEmpty()) return false;
        trainId = trainId.trim();

        if (head.train.getTrainId().equalsIgnoreCase(trainId)) {
            head = head.next;
            size--;
            return true;
        }
        TrainNode current = head;
        while (current.next != null) {
            if (current.next.train.getTrainId().equalsIgnoreCase(trainId)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // get the number of trains
    public int getSize(){
        return size;
    }

    // check if the list is empty

    public boolean isEmpty(){
        return head == null;
    }

}



