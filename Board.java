/* Dilara TOSUN - 20050111041*/
import java.util.*;
public class Board {
	private final static int X = 0;
	private final static int Y = 1;
	private final static int neighbors[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	public final int size;
	private final Set<Integer> pieces = new TreeSet<>();
	Board(int size) {
		this.size = size;
	}

	Board(Board board) {
		this.size = board.size;
		this.pieces.addAll(board.pieces);
	}
	Board(int size, int[] xs, int[] ys) {
		this.size = size;
		
		for (int i=0; i<xs.length; i++) {
			addPiece(xs[i], ys[i]);
		}
	}
	public boolean isInside(int x, int y) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}
	public boolean hasPiece(int x, int y) {
		if (isInside(x, y)) {
			int key = y * size + x;
			return pieces.contains(key);
		}
		else
			return false;
	}
	public void addPiece(int x, int y) {
		if (isInside(x, y)) {
			int key = y * size + x;
			pieces.add(key);
		}
	}
	public void removePiece(int x, int y) {
		if (isInside(x, y)) {		
			int key = y * size + x;
			pieces.remove(key);
		}
	}
	public Collection<Board> getSuccessors() {
		Set<Board> successors =  new HashSet<>();;
		pieces.forEach(piece -> {
			int x = piece % size;
			int y = piece / size;
			Arrays.stream(neighbors).forEach(neighbor -> {
				int nx = x + neighbor[X];
				int ny = y + neighbor[Y];
				if (isInside(nx, ny) && !hasPiece(nx, ny)) {
					Board successor = createSuccessorBoard(nx, ny);
					successors.add(successor);
				}
			});
		});
		return successors;
	}
	private Board createSuccessorBoard(int nx, int ny) {
		Board successor = new Board(this);
		successor.addPiece(nx, ny);
		return successor;
	}

	@Override
    public int hashCode() {
        return pieces.hashCode() + size;
    }

	@Override
	public boolean equals(Object o) {
		return this == o || o instanceof Board other && size == other.size && pieces.equals(other.pieces);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y=0; y<size; y++) {
			for (int x=0; x<size; x++) {
				if (hasPiece(x, y))
					sb.append("#");
				else
					sb.append(".");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
