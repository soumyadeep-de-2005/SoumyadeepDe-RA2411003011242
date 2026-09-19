package section3;

/**
 * Square extends Rectangle.
 * Overriding setWidth/setHeight forces both dimensions to be equal,
 * which violates the contract of Rectangle (LSP).
 */
public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width;
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
