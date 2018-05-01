package src.arc;

public class QueueNode<PageModel> {

    QueueNode prev;
    QueueNode next;

    int key;
    QueueTypeEnum type;
    PageModel data;

    // Constructor for initialization of QueueNode
    QueueNode() {
        this.key = Integer.MIN_VALUE;
        this.prev = this;
        this.next = this;
    }

    QueueNode(int key, PageModel data) {
        this.key = key;
        this.data = data;
    }

    public PageModel getData() {
        return data;
    }

    public void setData(PageModel data) {
        this.data = data;
    }

    public void addToLast(QueueNode head) {
        QueueNode tail = head.prev;
        head.prev = this;
        tail.next = this;
        next = head;
        prev = tail;
    }

    public void remove() {

        if (prev != null && next != null) {
            prev.next = next;
            next.prev = prev;
            prev = next = null;
            type = null;
        }
    }


}
