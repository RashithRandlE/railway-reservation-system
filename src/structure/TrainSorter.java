package structure;

import model.Train;
import java.util.List;

public class TrainSorter {

    // 1. BUBBLE SORT
    // Sort by Train ID
    public static void bubbleSortById(TrainLinkedList list) {
        if (list.head == null || list.head.next == null) {
            return;
        }
        boolean swapped;
        do {
            swapped = false;
            TrainLinkedList.TrainNode current = list.head;
            while (current.next != null) {
                if (current.train.getTrainId().compareToIgnoreCase(current.next.train.getTrainId()) > 0) {
                    swapTrainData(current, current.next);
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    // 2. SELECTION SORT
    // Sort by Train Name A-Z
    public static void selectionSortByName(TrainLinkedList list) {
        TrainLinkedList.TrainNode current = list.head;
        while (current != null) {
            TrainLinkedList.TrainNode minimum = current;
            TrainLinkedList.TrainNode search = current.next;

            while (search != null) {
                if (search.train.getTrainName().compareToIgnoreCase(minimum.train.getTrainName()) < 0) {
                    minimum = search;
                }
                search = search.next;
            }
            if (minimum != current) {
                swapTrainData(current, minimum);
            }
            current = current.next;
        }
    }

    // 3. INSERTION SORT
    // Sort by Available Seats
    // Lowest -> Highest
    public static void insertionSortByAvailableSeats(TrainLinkedList list) {
        if (list.head == null || list.head.next == null) {
            return;
        }
        TrainLinkedList.TrainNode sorted = null;
        TrainLinkedList.TrainNode current = list.head;

        while (current != null) {
            TrainLinkedList.TrainNode next = current.next;
            if (sorted == null || current.train.getAvailableSeats() <= sorted.train.getAvailableSeats()) {
                current.next = sorted;
                sorted = current;
            } else {
                TrainLinkedList.TrainNode search = sorted;

                while (search.next != null && search.next.train.getAvailableSeats() < current.train.getAvailableSeats()) {
                    search = search.next;
                }
                current.next = search.next;
                search.next = current;
            }
            current = next;
        }
        list.head = sorted;
    }

    // 4. MERGE SORT
    // Sort by Total Seats
    // Lowest -> Highest
    public static void mergeSortByTotalSeats(TrainLinkedList list) {
        if (list.head == null || list.head.next == null) {
            return;
        }
        list.head = mergeSortRecursive(list.head);
    }

    private static TrainLinkedList.TrainNode mergeSortRecursive(TrainLinkedList.TrainNode node) {
        if (node == null || node.next == null) {
            return node;
        }

        TrainLinkedList.TrainNode middle = getMiddle(node);
        TrainLinkedList.TrainNode right = middle.next;
        middle.next = null;

        TrainLinkedList.TrainNode leftSorted = mergeSortRecursive(node);
        TrainLinkedList.TrainNode rightSorted = mergeSortRecursive(right);
        return merge(leftSorted, rightSorted);
    }

    private static TrainLinkedList.TrainNode getMiddle(TrainLinkedList.TrainNode node) {
        TrainLinkedList.TrainNode slow = node;
        TrainLinkedList.TrainNode fast = node.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static TrainLinkedList.TrainNode merge(TrainLinkedList.TrainNode left, TrainLinkedList.TrainNode right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.train.getTotalSeats() <= right.train.getTotalSeats()) {
            left.next = merge(left.next, right);
            return left;
        } else {
            right.next = merge(left, right.next);
            return right;
        }
    }

    // 5. QUICK SORT
    // Sort by Start Station A-Z
    public static void quickSortByStartStation(TrainLinkedList list) {
        if (list.head == null || list.head.next == null) {
            return;
        }

        // Convert the linked-list data to an array/list of Train objects.
        List<Train> trains = list.getAllTrains();
        quickSortList(trains, 0, trains.size() - 1);

        // Put the sorted Train objects back into the linked list.
        TrainLinkedList.TrainNode current = list.head;
        for (Train train : trains) {
            current.train = train;
            current = current.next;
        }
    }

    // Quick Sort recursive
    private static void quickSortList(List<Train> trains, int low, int high) {
        if (low < high) {
            int pivotIndex = partitionList(trains, low, high);
            quickSortList(trains, low, pivotIndex - 1);
            quickSortList(trains, pivotIndex + 1, high);
        }
    }

    // Quick Sort partition
    private static int partitionList(List<Train> trains, int low, int high) {
        String pivot = trains.get(high).getStartStation();

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (trains.get(j).getStartStation().compareToIgnoreCase(pivot) <= 0) {
                i++;
                Train temp = trains.get(i);
                trains.set(i, trains.get(j));
                trains.set(j, temp);
            }
        }

        Train temp = trains.get(i + 1);
        trains.set(i + 1, trains.get(high));
        trains.set(high, temp);
        return i + 1;
    }

    // Swap only the Train objects.
    // The linked-list nodes themselves remain intact.
    private static void swapTrainData(TrainLinkedList.TrainNode first, TrainLinkedList.TrainNode second) {
        Train temp = first.train;
        first.train = second.train;
        second.train = temp;
    }
}
