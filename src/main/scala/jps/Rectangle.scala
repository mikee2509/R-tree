package jps

trait Bound {
    def coords: Coordinates

    def dims: Dimensions

    def x: Double = coords.x

    def y: Double = coords.y

    def x2: Double = x + width

    def y2: Double = y + height

    def width: Double = dims.width

    def height: Double = dims.height

    def enlargementToFit(bound: Bound): Double = {
        val expandLeft = if (bound.x < x) x - bound.x else 0
        val expandRight = if (x2 < bound.x2) bound.x2 - x2 else 0
        val expandBottom = if (bound.y < y) y - bound.y else 0
        val expandTop = if (y2 < bound.y2) bound.y2 - y2 else 0
        val expandedArea = (expandLeft + width + expandRight) * (expandBottom + height + expandTop)
        expandedArea - area
    }

    def area(): Double = width * height
}

case class Coordinates(x: Double, y: Double)

case class Dimensions(width: Double, height: Double)

/**
  * @param coords Left bottom corner of the rectangle
  * @param dims   Dimensions of the rectangle
  */
case class Rectangle(coords: Coordinates, dims: Dimensions) extends Bound

object Rectangle {
    def apply(bottomLeft: Coordinates, topRight: Coordinates): Rectangle = {
        Rectangle(bottomLeft, Dimensions(topRight.x - bottomLeft.x, topRight.y - bottomLeft.y))
    }

    val maxRect: Rectangle = {
        val minC = -Math.sqrt(Double.MaxValue)
        val d = -2.0 * minC
        Rectangle(Coordinates(minC, minC), Dimensions(d, d))
    }

    val minRect: Rectangle = Rectangle(Coordinates(0.0, 0.0), Dimensions(0.0, 0.0))
}

/**
  * Point is a rectangle with dimensions set to 0.0
  *
  * @param coords Point coordinates
  */
case class Point(override val coords: Coordinates) extends Bound {
    override def dims: Dimensions = Dimensions(0.0, 0.0)

    override def x2: Double = x

    override def y2: Double = y

    override def width: Double = 0.0

    override def height: Double = 0.0

    override def area(): Double = 0.0
}
