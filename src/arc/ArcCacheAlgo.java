package src.arc;

import src.database.DbConnection;
import src.models.PageModel;;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ArcCacheAlgo {

    DbConnection dbConnection = new DbConnection();

    private final HashMap<Integer, QueueNode<PageModel>> nodeData;

    private final QueueNode<PageModel> t1Head;
    private final QueueNode<PageModel> t2Head;
    private final QueueNode<PageModel> b1Head;
    private final QueueNode<PageModel> b2Head;

    private int p; // Adaptive P paramaeter
    private int t1Size;
    private int t2Size;
    private int b1Size;
    private int b2Size;
    private final int maxSizeOfCache;

//    Constructor
    public ArcCacheAlgo(int size) {
        this.maxSizeOfCache = size;
        this.nodeData = new HashMap<>();

        this.t1Head = new QueueNode<>();
        this.t2Head = new QueueNode<>();
        this.b1Head = new QueueNode<>();
        this.b2Head = new QueueNode<>();
    }


    public void cache(int key, PageModel value) {
        QueueNode<PageModel> queueNode = nodeData.get(key);

        if (queueNode == null) {
            onMissOnT1orT2orB1orB2(key, value);
        } else if (queueNode.type == QueueTypeEnum.B1) {
            queueNode.setData(value);
            onHitOnB1(queueNode);
        } else if (queueNode.type == QueueTypeEnum.B2) {
            queueNode.setData(value);
            onHitOnB2(queueNode);
        } else {
            queueNode.setData(value);
            onHitOnT1orT2(queueNode);
        }
    }

//    Hit in ARC( c) and DBL(2c)
    private void onHitOnT1orT2(QueueNode queueNode) {

        if (queueNode.type == QueueTypeEnum.T1) {
            t1Size--;
            t2Size++;
        }
        queueNode.remove();
        queueNode.type = QueueTypeEnum.T2;
        queueNode.addToLast(t2Head);
    }

//    miss in ARC(c), hit in DBL(2c)
    private void onHitOnB1(QueueNode queueNode) {

        p = Math.min(maxSizeOfCache, p + Math.max(b2Size / b1Size, 1));
        replace(queueNode);

        t2Size++;
        b1Size--;
        queueNode.remove();
        queueNode.type = QueueTypeEnum.T2;
        queueNode.addToLast(t2Head);
    }

//    miss in ARC(c), hit in DBL(2c)
    private void onHitOnB2(QueueNode queueNode) {

        p = Math.max(0, p - Math.max(b1Size / b2Size, 1));
        replace(queueNode);

        t2Size++;
        b2Size--;
        queueNode.remove();
        queueNode.type = QueueTypeEnum.T2;
        queueNode.addToLast(t2Head);
    }

//    miss in DBL(2c) and ARC(c)
    private void onMissOnT1orT2orB1orB2(int key, PageModel value) {

        QueueNode<PageModel> queueNode = new QueueNode<>(key, value);
        queueNode.type = QueueTypeEnum.T1;

        int sizeL1 = (t1Size + b1Size);
        int sizeL2 = (t2Size + b2Size);
        if (sizeL1 == maxSizeOfCache) {
            if (t1Size < maxSizeOfCache) {
                QueueNode<PageModel> queueNodeToBeRemoved = b1Head.next;
                removeDataFromQueue(queueNodeToBeRemoved);
                queueNodeToBeRemoved.remove();
                b1Size--;

                replace(queueNode);
            } else {
                QueueNode queueNodeToBeRemoved = t1Head.next;
                removeDataFromQueue(queueNodeToBeRemoved);
                queueNodeToBeRemoved.remove();
                t1Size--;
            }
        } else if ((sizeL1 < maxSizeOfCache) && ((sizeL1 + sizeL2) >= maxSizeOfCache)) {
            if ((sizeL1 + sizeL2) >= (2 * maxSizeOfCache)) {
                QueueNode<PageModel> queueNodeToBeRemoved = b2Head.next;
                removeDataFromQueue(queueNodeToBeRemoved);
                queueNodeToBeRemoved.remove();
                b2Size--;
            }
            replace(queueNode);
        }

        t1Size++;
        nodeData.put(key, queueNode);
        queueNode.addToLast(t1Head);

    }

//    Replace function
    private void replace(QueueNode candidate) {

        if ((t1Size >= 1) && (((candidate.type == QueueTypeEnum.B2) && (t1Size == p)) || (t1Size > p))) {
            QueueNode<PageModel> queueNodeToBeRemoved = t1Head.next;
            queueNodeToBeRemoved.remove();
            queueNodeToBeRemoved.type = QueueTypeEnum.B1;
            queueNodeToBeRemoved.addToLast(b1Head);
            t1Size--;
            b1Size++;
        } else {
            QueueNode<PageModel> queueNodeToBeRemoved = t2Head.next;
            queueNodeToBeRemoved.remove();
            queueNodeToBeRemoved.type = QueueTypeEnum.B2;
            queueNodeToBeRemoved.addToLast(b2Head);
            t2Size--;
            b2Size++;
        }

    }

//    Remove Data from Queue and push into archives i.e, src.database
     public void removeDataFromQueue(QueueNode<PageModel> queueNodeToBeRemoved) {
        nodeData.remove(queueNodeToBeRemoved.key);
        PageModel data = queueNodeToBeRemoved.getData();
        try {
            dbConnection.saveUpdate(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void printCacheIdsFromQueue() {
        String keys = "";
        System.out.println("Keys found in Cache: ");
        for (Map.Entry<Integer, QueueNode<PageModel>> entry : nodeData.entrySet()) {
            Integer key = entry.getKey();
            keys += key + " ";
            System.out.println("Key: " + key);
        }
        System.out.println("Keys found in Cache are: " + keys);
    }

}
