package us.lsi.basictypes;

import java.lang.reflect.Array;
import java.util.Arrays;

import us.lsi.common.Preconditions;

/**
 * Una implementación de un array de tamaño variable
 * 
 * @author Miguel Toro
 *
 * @param <E> Tipo de los elementos
 */
public class AList<E> {
	
	public static <E> AList<E> empty() {
		return new AList<E>();
	}

	public static <E> AList<E> create(int capacity) {
		return new AList<E>(capacity);
	}

	public static <E> AList<E> create(AList<E> a) {
		return new AList<E>(a);
	}

	public static <E> AList<E> create(E[] a) {
		return new AList<E>(a);
	}

	private int capacity;
	private int size;
	private E[] elements;
	private final int INITIAL_CAPACITY = 10;
	
	private AList() {
		super();
		this.capacity = INITIAL_CAPACITY;
		this.size = 0;
		this.elements = null;
	}
	
	private AList(int capacity) {
		super();
		this.capacity = capacity;
		this.size = 0;
		this.elements = null;
	}
	
	private AList(AList<E> a) {
		super();
		this.capacity = a.capacity;
		this.size = a.size();
		this.elements = Arrays.copyOf(a.elements,a.capacity);
	}
	
	private AList(E[] a) {
		super();
		this.capacity = a.length;
		this.size = capacity;
		this.elements = Arrays.copyOf(a, capacity);
	}

    private void grow(){
    	if(size==capacity){
    		E[] oldElements = elements;
    		capacity = capacity*2;
    		elements = Arrays.copyOf(oldElements, capacity);
    	}
    }
	
    public int size() {
		return size;
	}

    public boolean isEmpty(){
    	return size == 0;
    }
    
	public E get(int index) {
    	Preconditions.checkElementIndex(index, size);
		return elements[index];
	}
    
	@SuppressWarnings("unchecked")
	public E set(int index, E e){	
		Preconditions.checkPositionIndex(index,this.size);
		if(this.elements == null) {
			this.elements = (E[]) Array.newInstance(e.getClass(), capacity);
		}
		if(index == this.size) {
			this.size = this.size +1;
			grow();
		}
		E r = get(index);
		elements[index]= e;
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public boolean add(E e) {
		if(this.elements == null) {
			this.elements = (E[]) Array.newInstance(e.getClass(), capacity);
		}
		grow();
		elements[size] = e;
		size++;   	
		return true;
	}
	
	public void add(int index, E e) {
		Preconditions.checkPositionIndex(index, size);
		add(e);
		// size ya ha quedado aumentado
		for(int i = size-1; i > index; i--){
			elements[i]= elements[i-1];
		}
		elements[index]=e;
	}
	
	public E remove(int index) {
		Preconditions.checkElementIndex(index, size);
		E e = elements[index];
		for(int i = index; i < size-1; i++){
			elements[i]= elements[i+1];
		}
		size --;
		return e;
	}
	
	public E[] toArray(){
		E[] r = Arrays.copyOf(this.elements, size);
		return r;
	}
	
	public String toString(){
		String s = "{";
		boolean prim = true;
		for(int i=0; i<size; i++){
			if(prim){
				prim = false;
				s = s+elements[i];
			}else{
				s = s+","+elements[i];
			}
		}
		s = s+"}";
		return s;
	}
}
