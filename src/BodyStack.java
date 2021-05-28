import java.util.Stack;

public class BodyStack {
    private StackNode head;

    public BodyStack(Body body){
        push(body);
    }

    public BodyStack(){
    }

    public void push (Body body){
        if (head == null){
            head = new StackNode(body);
        }
        else{
            StackNode otherNode = head;
            head = new StackNode(body, otherNode);
        }
    }

    public Body pop (){
        if(head == null){
            return null;
        }else{
            StackNode returnValue = head;
            head = head.next;
            return returnValue.body;
        }
    }

    public class StackNode{
        private Body body;
        private StackNode next;

        public StackNode(Body body){
            this.body = body;
        }

        public StackNode(Body body, StackNode next){
            this.body = body;
            this.next = next;
        }
    }
}
