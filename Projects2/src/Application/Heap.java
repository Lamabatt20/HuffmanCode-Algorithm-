package Application;



public class Heap {
	Node[] heap;
	int capacity;
	int size;

	public Heap() {
		this(1);
	}

	public Heap(int capacity) {
		this.capacity = capacity;
		this.size = 0;
		this.heap = new Node[capacity];
	}

	public Node[] getHeap() {
		return heap;
	}

	public void setHeap(Node[] heap) {
		this.heap = heap;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void insert(Node element) {
		int i = ++size;
		while ((i > 1) && element.frequency < heap[i / 2].frequency) {
			heap[i] = heap[i / 2];
			i /= 2;

		}
		heap[i] = element;
	}

	public Node remove() {
		if (isEmpty()) {
			return null;
		}
		int child, i;
		Node last, ptr = null;
		if (size != 0) {
			ptr = heap[1];
			last = heap[size--];
			for (i = 1; i * 2 <= size; i = child) {
				child = i * 2;
				if (child < size && heap[child].getFrequency() > heap[child + 1].getFrequency())
					child++;
				if (last.getFrequency() > heap[child].getFrequency())
					heap[i] = heap[child];
				else
					break;
			}
			heap[i] = last;

		}
		return ptr;
	}
	public Node[] get() {
		Node[] result = new Node[size + 1];
		for (int i = 0; i < size + 1; i++)
			result[i] = heap[i];
		return result;
	}

}
