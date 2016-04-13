import java.lang.Number;
import java.lang.IllegalArgumentException;
public class Matrix2D{
	private float[][] matrix;
	private int dimA;
	private int dimB;

	public Matrix2D(float[][] array2d){
		this.matrix = array2d;
		dimA = array2d.length;
		dimB = array2d[0].length;
	}

	public Matrix2D(float[] array1d){
		this.matrix = new float[1][];
		this.matrix[0] = array1d;
		dimA = 1;
		dimB = array1d.length;
	}

	public Matrix2D(float scalar){
		this.matrix = new float[1][1];
		this.matrix[0][0] = scalar;
		dimA = dimB = 1;
	}

	/* ####### */
	/* GETTERS */
	/* ####### */

	public int getDimension(int dimension){
		if (dimension==0) return dimA;
		else if (dimension==1) return dimB;
		else throw new IllegalArgumentException("Dimension is 0 or 1");
	}

	public float getElement(int i, int j){
		return matrix[i][j];
	}

	public float getElement(int i){
		return matrix[0][i];
	}

	/* ########## */
	/* OPERATIONS */
	/* ########## */

	/** Adds this matrix with another*/
	public Matrix2D add(Matrix2D other){
		if (dimA != other.getDimension(0) || dimB != other.getDimension(1))
			throw new IllegalArgumentException("Matrices must be same size for addition");

		float[][] sum = new float[dimA][dimB];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<dimB;j++)
				sum[i][j] = matrix[i][j] + other.getElement(i,j);

		return new Matrix2D(sum);
	}

	/** Multiplies this matrix with another*/
	public Matrix2D multiply(Matrix2D other){
		if (dimB != other.getDimension(0))
			throw new IllegalArgumentException("The second dimension of the first matrix must be equal to the first dimension of the second matrix");

		float[][] multiplied = new float[dimA][other.getDimension(1)];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<other.getDimension(1);j++){
				multiplied[i][j] = 0;
				for (int a=0; a < dimB;a++)
					multiplied[i][j] += matrix[i][a] * other.getElement(a,j);
			}

		return new Matrix2D(multiplied);
	}

	public Matrix2D multiply(float scalar){
		float[][] multiplied = new float[dimA][dimB];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<dimB;j++)
				multiplied[i][j] = matrix[i][j]*scalar;

		return new Matrix2D(multiplied);
	}

	/** Transposes this matrix*/
	public Matrix2D transpose(){
		float[][] transposed = new float[dimB][dimA];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<dimB;j++)
				transposed[j][i] = matrix[i][j];

		return new Matrix2D(transposed);
	}

	/** Inverts a 2x2 matrix. */
	public Matrix2D invert(){
		if (dimA!=2 || dimB!= 2)
			throw new IllegalArgumentException("Can only invert 2x2 matrix");

		float det = (matrix[0][0] * matrix[1][1]) - (matrix[1][0]*matrix[0][1]);

		if (det == 0)
			throw new IllegalArgumentException("Matrix is singular and cannot be inverted");

		float[][] inverse = new float[2][2];
		inverse[0][0] = matrix[1][1] / det;
		inverse[1][1] = matrix[0][0] / det;
		inverse[1][0] = -matrix[1][0] / det;
		inverse[0][1] = -matrix[0][1] / det;

		return new Matrix2D(inverse);
	}

	/** Prints a matrix to stdout*/
	public String toString(){
		String s = "";
		for (int i=0; i<dimA;i++){
			if (i==0)
				s += ("┌\t");
			else
				s+=("│\t");

			for (int j=0;j<dimB;j++)
				s+=(matrix[i][j] + "\t");

			if (i < dimA-1)
				s+=("│\n");
			else
				s+=("┘\n");
		}
		return s;
	}

	/* ############## */
	/* STATIC HELPERS */
	/* ############## */

	public static Matrix2D getIdentity(int size){
		float[][] i = new float[size][size];
		for (int s=0;s<size;s++)
			i[s][s] = 1;
		return new Matrix2D(i);
	}

	////////////////////
	// Testing
	////////////////////

	public static void main(String[] args){
		System.out.println("~~ A ~~~~~~~");
		Matrix2D a = new Matrix2D(new float[]{1,2,3});
		System.out.println(a);
		System.out.println("Transpose");
		System.out.println(a.transpose());

		System.out.println("~~ B ~~~~~~~");
		Matrix2D b = new Matrix2D(new float[][]{{1,2},{3,4}});
		System.out.println(b);
		System.out.println("Transpose");
		System.out.println(b.transpose());
		System.out.println("Inverse");
		System.out.println(b.invert());
		System.out.println("Add to self");
		System.out.println(b.add(b));
		System.out.println("Inverse addition");
		System.out.println(b.add(b).invert());

		System.out.println("~~ C ~~~~~~~");
		Matrix2D c = new Matrix2D(new float[][]{{1,1,1},{2,3,4}});
		System.out.println(c);
		System.out.println("B x C");
		System.out.println(b.multiply(c));
		System.out.println("C-transposed x B");
		System.out.println(c.transpose().multiply(b));
		System.out.println("A x C-transposed");
		System.out.println(a.multiply(c.transpose()));

		System.out.println("~~ ERROR CHECKING ~~~~~~~");
		System.out.println("A + B");
		try {a.add(b);} catch (Exception e) {System.out.println(e);}
		System.out.println("C X B");
		try {c.multiply(b);} catch (Exception e) {System.out.println(e);}
		System.out.println("Invert C");
		try {c.invert();} catch (Exception e) {System.out.println(e);}
		System.out.println("Invert singular matrix");
		Matrix2D d = new Matrix2D(new float[][]{{1,1},{1,1}});
		try {d.invert();} catch (Exception e) {System.out.println(e);}

	}
}