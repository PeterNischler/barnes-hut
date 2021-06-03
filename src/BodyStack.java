import java.util.Stack;
// wird nicht benötigt

public class BodyStack {
    private StackNode head;

    public BodyStack(Body body) {
        push(body);
    }

    public BodyStack() {
    }

    public void push(Body body) {
        if (head == null) {
            head = new StackNode(body);
        } else {
            StackNode otherNode = head;
            head = new StackNode(body, otherNode);
        }
    }

    public Body pop() {
        if (head == null) {
            return null;
        } else {
            StackNode returnValue = head;
            head = head.next;
            return returnValue.body;
        }
    }

    public Body peek() {
        if (head == null) {
            return null;
        } else {
            return head.body;
        }
    }

    public Boolean moreThanOneBody() {
        if (head == null) {
            return false;
        }
        if (head.next.next != null) {
            return true;
        } else {
            return false;
        }
    }

    public class StackNode {
        private Body body;
        private StackNode next;

        public StackNode(Body body) {
            this.body = body;
        }

        public StackNode(Body body, StackNode next) {
            this.body = body;
            this.next = next;
        }
    }
}
