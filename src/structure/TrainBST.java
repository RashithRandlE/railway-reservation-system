package structure;
import model.Train;

public class TrainBST {

    //node shows the one train in the BST
    private static class Node{
        Train train;
        Node left;
        Node right;
    }
    private Node root;

    //insert method we used to add a new train into the BST
    public void insert(Train train){
        if(train == null){
            //using exception prevent from inserting a null train
            throw new IllegalArgumentException("Train cannot be null");
        }

        Node newNode = new Node();
        newNode.train = train;

        //tree is empty,make the new node root
        if(root == null){
            root = newNode;
            return;
        }
        Node current = root;

        while(true){
            //this compare the new train ID with the current train ID
            int compare = train.getTrainId().compareToIgnoreCase(current.train.getTrainId());

            //this check duplicates
            if(compare == 0){
                System.out.println("Duplicate Train ID : " + train.getTrainId());
                return;
            }
            //new ID is smaller, goes to the left
            if(compare < 0){
                if(current.left == null){
                    current.left = newNode;
                    return;
                }
                current = current.left;

                //new ID is larger, goes to the right
            }else{
                if(current.right == null) {
                    current.right = newNode;
                    return;
                }
                current = current.right;
            }
        }
    }
    //we used search method to find a train by its ID
    public Train search(String trainId){
        //train ID is empty,returns null
        if(trainId == null || trainId.trim().isEmpty())
            return null;
        trainId = trainId.trim();
        Node current = root;

        while(current != null){
            //train found when train IDs are equal
            if(trainId.equalsIgnoreCase(current.train.getTrainId())){
                return current.train;
            }
            //ID is smaller searches in the left side
            if(trainId.compareToIgnoreCase(current.train.getTrainId()) < 0){
                current = current.left;

                //ID is larger searches in the right side
            }else {
                current = current.right;
            }
        }
        return null;
    }
    //we used delete method to delete a train by its Train ID
    public void delete(String trainId){
        //train ID empty, do nothing
        if(trainId == null || trainId.trim().isEmpty())
            return;
        root = deleteNode(root, trainId.trim());
    }
    private Node deleteNode(Node root, String trainId){
        if(root == null)
            return null;

        //ID is smaller,searches in left sub tree
        if(trainId.compareToIgnoreCase(root.train.getTrainId()) < 0){
            root.left = deleteNode(root.left, trainId);

            //ID is larger, searches in right sub tree
        }else if(trainId.compareToIgnoreCase(root.train.getTrainId()) > 0) {
            root.right = deleteNode(root.right, trainId);

        }else {
            if(root.left == null )
                return root.right;
            if(root.right == null )
                return root.left;

            Node temp = root.right;

            while(temp.left != null)
                temp = temp.left;

            root.train = temp.train;
            root.right = deleteNode(root.right, temp.train.getTrainId());
        }
        return root;
    }

    //display train IDs in sorted order
    //Inorder (left -> root -> right)
    private void inorder(Node node){
        if(node != null) {
            inorder(node.left);
            node.train.displayTrainInfo();
            inorder(node.right);
        }
    }
    //Preorder (root -> left -> right)
    private void preorder(Node node){
        if(node != null) {
            node.train.displayTrainInfo();
            preorder(node.left);
            preorder(node.right);
        }
    }
    //Postorder (left -> right -> root)
    private void postorder(Node node){
        if(node != null) {
            postorder(node.left);
            postorder(node.right);
            node.train.displayTrainInfo();
        }
    }
    //inorder traversal start from root
    public void inorder(){
        inorder(root);
    }
    //preorder traversal start from root
    public void preorder(){
        preorder(root);
    }
    //postorder traversal start from root
    public void postorder(){
        postorder(root);
    }

    //return the root node
    public Train getRoot(){
        return root == null ? null : root.train;
    }

}