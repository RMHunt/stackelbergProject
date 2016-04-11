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

	public Matrix2D add(Matrix2D other){
		if (dimA != other.getDimension(0) || dimB != other.getDimension(1))
			throw new IllegalArgumentException("Matrices must be same size for addition");

		float[][] sum = new float[dimA][dimB];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<dimB;j++)
				sum[i][j] = matrix[i][j] + other.getElement(i,j);

		return new Matrix2D(sum);
	}

	public Matrix2D multiply(Matrix2D other){
		if (dimB != other.getDimension(0))
			throw new IllegalArgumentException("The second dimension of the first matrix must be equal to the first dimension of the second matrix");

		float[][] multiplied = new float[dimA][other.getDimension(1)];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<other.getDimension(1);j++){
				multiplied[i][j] = 0;
				for (int a=0; a < dimB;a++)
					multiplied[i][j] += matrix[i][j+a] * other.getElement(i+a,j);
			}

		return new Matrix2D(multiplied);
	}

	public Matrix2D transpose(){
		float[][] transposed = new float[dimB][dimA];
		for (int i=0;i<dimA;i++)
			for (int j=0;j<dimB;j++)
				transposed[j][i] = matrix[i][j];

		return new Matrix2D(transposed);
	}

	public Matrix2D invert(){
		return;
	}
}