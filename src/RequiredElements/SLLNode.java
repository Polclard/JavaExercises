package RequiredElements;
public class SLLNode<E> {
	protected E element;
	protected SLLNode<E> succ;

	public SLLNode(E elem, SLLNode<E> succ) {
		this.element = elem;
		this.succ = succ;
	}


	void insertLast(E element)
	{
		SLLNode<E> elementNode = new SLLNode<>(element, succ);
		succ = elementNode;
	}

	@Override
	public String toString() {
		return element.toString();
	}

	public E getElement() {
		return element;
	}


	public SLLNode<E> getSucc() {
		return succ;
	}

}
