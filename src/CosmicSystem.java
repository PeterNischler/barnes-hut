//This class represents a linked list for objects of class 'Body'
public class CosmicSystem {

    //DONE: Define variables.
    private String name;
    private CSnode head; //0th node
    private CSnode nil;



    // Initialises this system as an empty system with a name.
    public CosmicSystem(String name) {
        //DONE: implement method.
        this.name = name;
    }

    // Adds 'body' to the end of the list of bodies if the list does not already contain a
    // body with the same name as 'body', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(Body body) {
        //DONE: implement method.
        if (head == null){
            head = new CSnode(body);
            return true;
        }

        CSnode current = head;

        while (current.next != null){
            // return false if body already in list
            if (current.body.getName().equals(body.getName())){
                return false;
            }
            current = current.next;
        }
        current.next = new CSnode(body);
        //
        current.next.prev = current;
        return true;
    }

    // Returns the 'body' with the index 'i'. The body that was first added to the list has the
    // index 0, the body that was most recently added to the list has the largest index (size()-1).
    // Precondition: 'i' is a valid index.
    //return null if index is > size()
    public Body get(int i) {
        //DONE: implement method.
        if (i == 0){
            return head.body;
        }

        CSnode current = head;
        int j = 1;
        //travers list until the index of the current node (j) is < than i
        while (j <= i){
            current = current.next;
            j++;
        }
        return current.body;
    }
    //returns node with index i
    public CSnode getCSnode(int i) {
        //DONE: implement method.
        if (i == 0){
            return head;
        }

        CSnode current = head;
        int j = 1;
        //travers list until the index of the current node (j) is < than i
        while (j <= i){
            current = current.next;
            j++;
        }
        return current;
    }

    // Returns the body with the specified name or 'null' if no such body exits in the list.
    public Body get(String name) {
        //DONE: implement method.
        CSnode current = head;
        while (current != null){
            if (current.body.getName().equals(name)){
                break;
            }
            current = current.next;
        }
        if (current == null){
            return null;
        }
        return current.body;
    }

    // Returns the body with the same name as the input body or 'null' if no such body exits in the list.
    public Body get(Body body) {
        //DONE: implement method.
        return get(body.getName());
    }

    // returns the number of entries of the list.
    public int size() {
        //DONE: implement method.
        if (head == null){
            return 0;
        }

        CSnode current = head;
        int i = 1;
        while (current.next != null){
            current = current.next;
            i++;
        }
        return i;
    }

    //AB3 start

    // Inserts the specified 'body' at the specified position
    // in this list if 'i' is a valid index and there is no body
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and
    // any subsequent elements to the right (adds 1 to their
    // indices). The first element of the list has the index 0.
    // Returns 'true' if the list was changed as a result of
    // the call, 'false' otherwise.
    public boolean add(int i, Body body) {
        System.out.println(body.getName());
        //Done: implement method.
        if (i > size() || i < 0){
            return false;
        }
        //check if body is not in list
        if (get(body.getName()) != null){
            return false;
        }

        //ad at index 0
        if (i == 0){
            CSnode temp = head;
            head = new CSnode(body, temp);
            return true;
        }

        //add at any other index
        int j = 0;
        CSnode current = head;

        //move up to i - 1
        while (j < i - 1){
            j++;
            current = current.next;
        }

        //insert after i -1
        if (current.next != null){
            CSnode temp = current.next;
            CSnode temp_next =
                    current.next = new CSnode(body, temp);
            current.next.prev = current;
            current.next.next.prev = current.next;

        } else{
            current.next = new CSnode(body);
            current.next.prev = current;

        }
        return true;
    }

    //removes the body at index i from the list, if i is a valid index
    //returns true if removal was done, and false otherwise (invalid index)
    public boolean remove(int i) {
        //TODO: implement method.
        return false;
    }

    //removes a body from the list, if the list contains a body with the same name as the input body
    //returns true if removal was done, and false otherwise (no body with the same name)
    public boolean remove(Body body) {
        //TODO: implement method.
        return false;
    }

    // Returns a new list that contains the same elements as this list in reverse order. The list 'this'
    // is not changed and only the references to the bodies are copied, not their content (shallow copy).
    public CosmicSystem reverse() {
        CosmicSystem res = new CosmicSystem("reverse-" + name);
        CSnode current = getCSnode(size()-1);
        while (current != null){
            res.add(current.body);
            current = current.prev;
        }

        return res;
    }

    // Returns a readable representation with the name of the system and all bodies in order of the list.
    // E.g.,
    // Jupiter System:
    // Jupiter, 1.898E27 kg, radius: 6.9911E7 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    // Io, 8.9E22 kg, radius: 1822000.0 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    //
    //Hint: also use toString() in Body.java for this.
    public String toString() {
        String res = name + ": {";
        CSnode current = head;

        while (current != null){
            res = res + current.body + ", ";
            current = current.next;
        }

        return res + "}";
    }
    //AB3 end

    public CSnode getHead(){
        return head;
    }

    public class CSnode {

        private Body body;
        private CSnode next;
        private CSnode prev;

        public CSnode(Body body){
            this.body = body;
        }

        public CSnode(Body body, CSnode next){
            this.body = body;
            this.next = next;
        }

        public CSnode(Body body, CSnode next, CSnode prev){
            this.body = body;
            this.next = next;
            this.prev = prev;
        }

        public CSnode getNext() {
            return next;
        }

        public Body getBody() {
            return body;
        }

        public String toString(){
            String prev_str;
            String next_str;
            if (prev == null){
                prev_str = "prev: null\n";
            } else{
                prev_str = "prev: " + prev.body.toString() + "\n";
            }

            if (next == null){
                next_str = "next: null\n";
            } else{
                next_str = "next: " + next.body.toString()+ "\n";
            }

            return prev_str +  "this: " + body.toString() + "\n" + next_str;
        }
    }
}


