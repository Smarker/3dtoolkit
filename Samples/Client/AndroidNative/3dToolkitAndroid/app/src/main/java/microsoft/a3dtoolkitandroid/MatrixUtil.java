package microsoft.a3dtoolkitandroid;

import android.opengl.Matrix;
import android.util.Log;

public class MatrixUtil {
    private static float[] IDENTITY = {
        1.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 1.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 1.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 1.0f
    };

    private static float[] ZERO = {
        0.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 0.0f,
        0.0f, 0.0f, 0.0f, 0.0f
    };

    public static float[] createMatrix() {
        return IDENTITY;
    }

    public static float[] cloneMatrix(float[] matrix) {
        float[] clone = IDENTITY;

        for (int i = 0; i < matrix.length; i++) {
            clone[i] = matrix[i];
        }

        return clone;
    }



    /*
    float[] l = { 2.0f, 0.0f, 0.0f, 0.0f,   3.0f, 1.0f, 0.0f, 0.0f,  0.0f, 0.0f, 1.0f, 0.0f,  0.0f, 0.0f, 0.0f, 1.0f, };
    float[] r = { 1.0f, 0.0f, 0.0f, 0.0f,   2.0f, 1.0f, 0.0f, 0.0f,  0.0f, 0.0f, 1.0f, 0.0f,  0.0f, 0.0f, 0.0f, 8.0f, };
    Log.d("matrix mult", formatMatrix(l) + " * \n" + formatMatrix(r) + " = \n" + formatMatrix(result));
    */
    public static float[] multiplyMatrices(float[] leftMatrix, float[] rightMatrix) {
        float[] productMatrix = ZERO;
        int productMatrixOffset = 0;
        int leftMatrixOffset = 0;
        int rightMatrixOffset = 0;

        Matrix.multiplyMM(productMatrix, productMatrixOffset, leftMatrix, leftMatrixOffset, rightMatrix, rightMatrixOffset);

        return productMatrix;
    }

    public static float[] translateMatrix(float[] translationFactorVector) {
        float[] translationMatrix = ZERO;
        float[] sourceMatrix = IDENTITY;
        float translationFactorX = translationFactorVector[0];
        float translationFactorY = translationFactorVector[1];
        float translationFactorZ = translationFactorVector[2];
        int translationMatrixOffset = 0;
        int sourceMatrixOffset = 0;

        Matrix.translateM(translationMatrix, translationMatrixOffset, sourceMatrix, sourceMatrixOffset, translationFactorX, translationFactorY, translationFactorZ);

        Log.d("matrix translate", printMatrix(translationMatrix));

        return translationMatrix;
    }

    public static float[] rotateX(double theta) {
        float sinTheta = (float)Math.sin(theta);
        float cosTheta = (float)Math.cos(theta);

        float[] rotationMatrix = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, cosTheta, sinTheta, 0.0f,
            0.0f, -sinTheta, cosTheta, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
        };

        return rotationMatrix;
    }

    public static float[] rotateY(double theta) {
        float sinTheta = (float)Math.sin(theta);
        float cosTheta = (float)Math.cos(theta);

        float[] rotationMatrix = {
                cosTheta, 0.0f, -sinTheta, 0.0f,
                0.0f, 1.0f, 0.0f, 0.0f,
                sinTheta, 0.0f, cosTheta, 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f
        };

        return rotationMatrix;
    }

    public static float[] rotateZ(double theta) {
        float sinTheta = (float)Math.sin(theta);
        float cosTheta = (float)Math.cos(theta);

        float[] rotationMatrix = {
            cosTheta, sinTheta, 0.0f, 0.0f,
            -sinTheta, cosTheta, 0.0f, 0.0f,
            0.0f, 0.0f, 0.1f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
        };

        return rotationMatrix;
    }


    public static String printMatrix(float[] matrix) {
        float[] result = { 0.0f, 0.0f, 0.0f, 0.0f,   0.0f, 0.0f, 0.0f, 0.0f,   0.0f, 0.0f, 0.0f, 0.0f,   0.0f, 0.0f, 0.0f, 0.0f };

        Matrix.transposeM(result, 0, matrix, 0);
        StringBuilder sbResult = new StringBuilder();
        sbResult.append("\n");

        for(int i = 0, j = 0; i < 16; i++, j++) {
            sbResult.append(result[i] + " ");
            if (j == 3 || j == 7 || j == 11) {
                sbResult.append("\n");
            }
        }

        return sbResult.toString();
    }
}
