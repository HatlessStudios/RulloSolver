package uk.co.hatless_studios.rullo;

import java.util.Deque;

class Row {
    private Node[] nodes;
    private Row[] rows;
    private int aim;

    Row(Node[] nodes, int aim) {
        this(nodes, new Row[nodes.length], aim);
    }

    Row(Node[] nodes, Row[] rows, int aim) {
        this.nodes = nodes;
        this.rows = rows;
        this.aim = aim;
    }

    Node[] getNodes() {
        return nodes;
    }

    Row[] getRows() {
        return rows;
    }

    int getAim() {
        return aim;
    }

    void setState(Deque<Row> queue, int index, Runnable callback) {
        if (nodes[index].isOn()) {
            queue.add(rows[index]);
            queue.add(this);
            callback.run();
        }
        nodes[index].setState();
    }

    void setLock(Deque<Row> queue, int index, Runnable callback) {
        if (!nodes[index].isLocked()) {
            queue.add(rows[index]);
            callback.run();
        }
        nodes[index].setLock();
    }
}
