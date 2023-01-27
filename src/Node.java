import java.util.LinkedList;
import java.util.Queue;

class Node {
    private int inboundLimit;
    private int outboundLimit;
    //counts hold the total numbers and helps us compare it we dont exceed the above limits.
    private int inboundCount;
    private int outboundCount;
    //FIFO all in queue processed first come as first served basis
    private Queue<Integer> inboundQueue;
    private Queue<Integer> outboundQueue;

    public Node(int inboundLimit, int outboundLimit) {
        this.inboundLimit = inboundLimit;
        this.outboundLimit = outboundLimit;
        this.inboundCount = 0;
        this.outboundCount = 0;
        this.inboundQueue = new LinkedList<>();
        this.outboundQueue = new LinkedList<>();
    }

    public void addInboundPackage(int count) {
        if (inboundCount + count <= inboundLimit) {
            inboundCount += count;
        } else {
            // what can't be shipped(above limit) at this time is put in queue.
            int remaining = inboundLimit - inboundCount;
            inboundCount = inboundLimit;
            inboundQueue.offer(count - remaining);
            processInboundQueue();
        }
    }

    public void addOutboundPackage(int count) {
        if (outboundCount + count <= outboundLimit) {
            outboundCount += count;
        } else {
            int remaining = outboundLimit - outboundCount;
            outboundCount = outboundLimit;
            outboundQueue.offer(count - remaining);
            processOutboundQueue();
        }
    }

    //everytime we go below the limit for inbound or outbound, we process whats on the queue
    public void processInboundQueue() {
        while (inboundQueue.size() > 0 && inboundCount < inboundLimit) {
            int count = inboundQueue.poll();
            if (count + inboundCount <= inboundLimit) {
                inboundCount += count;
            } else {
                int remaining = inboundLimit - inboundCount;
                inboundCount = inboundLimit;
                inboundQueue.offer(count - remaining);
            }
        }
    }

    public void processOutboundQueue() {
        while (outboundQueue.size() > 0 && outboundCount < outboundLimit) {
            int count = outboundQueue.poll();
            if (count + outboundCount <= outboundLimit) {
                outboundCount += count;
            } else {
                int remaining = outboundLimit - outboundCount;
                outboundCount = outboundLimit;
                outboundQueue.offer(count - remaining);
            }
        }
    }

    public int getInboundCount() {
        return inboundCount;
    }

    public int getOutboundCount() {
        return outboundCount;
    }

    public static void main(String[] args) {
        Node NodeA = new Node(10, 15);
        Node NodeB = new Node(20, 25);
        Node NodeC = new Node(30, 35);
        Node NodeD = new Node(40, 45);
        
    
        NodeA.addInboundPackage(5);
        System.out.println("Inbound count  Node a: " + NodeA.getInboundCount());
        NodeA.addInboundPackage(8);
        System.out.println("Inbound count  Node a: " + NodeA.getInboundCount());
        NodeA.addOutboundPackage(12);
        System.out.println("Outbound count Node a " + NodeA.getOutboundCount());
    
        NodeB.addInboundPackage(10);
        System.out.println("Inbound count  Node b: " + NodeB.getInboundCount());
        NodeB.addInboundPackage(12);
        System.out.println("Inbound count  Node b " + NodeB.getInboundCount());
        NodeB.addOutboundPackage(20);
        System.out.println("Outbound count Node b " + NodeB.getOutboundCount());
    
        NodeC.addInboundPackage(15);
        System.out.println("Inbound count Node c " + NodeC.getInboundCount());
        NodeC.addInboundPackage(18);
        System.out.println("Inbound count Node c" + NodeC.getInboundCount());
        NodeC.addOutboundPackage(25);
        System.out.println("Outbound count Node c " + NodeC.getOutboundCount());
    
        NodeD.addInboundPackage(20);
        System.out.println("Inbound count Node d " + NodeD.getInboundCount());
        NodeD.addInboundPackage(22);
        System.out.println("Inbound count Node d  " + NodeD.getInboundCount());
        NodeD.addOutboundPackage(30);
        System.out.println("Outbound count Node d " + NodeD.getOutboundCount());
    
    }
}