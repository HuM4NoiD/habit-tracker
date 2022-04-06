package io.humanoid.habittracker.ui.util

import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CornerShapeable(
    val corners: ShapeCorners = ShapeCorners()
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val topStartSize = corners.topStart.size.value * density.density
        val topEndSize = corners.topEnd.size.value * density.density
        val bottomStartSize = corners.bottomStart.size.value * density.density
        val bottomEndSize = corners.bottomEnd.size.value * density.density

        return Outline.Generic(
            Path().apply {
                moveTo(0f, topStartSize)

                topStart(size, topStartSize, corners.topStart.type)

                lineTo(size.width - topEndSize, 0f)

                topEnd(size, topEndSize, corners.topEnd.type)

                lineTo(size.width, size.height - bottomEndSize)

                bottomEnd(size, bottomEndSize, corners.bottomEnd.type)

                lineTo(bottomStartSize, size.height)

                bottomStart(size, bottomStartSize, corners.bottomStart.type)

                close()
            }
        )
    }

    private fun Path.topStart(shapeSize: Size, cornerSize: Float, cornerType: Corner.Type) {
        when (cornerType) {
            Corner.Type.CUT -> {
                lineTo(cornerSize, 0f)
            }
            Corner.Type.ROUND -> {
                arcTo(
                    rect = Rect(Offset.Zero, square(cornerSize)),
                    startAngleDegrees = 180f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            Corner.Type.NONE -> {
                lineTo(0f, 0f)
            }
        }
    }

    private fun Path.topEnd(shapeSize: Size, cornerSize: Float, cornerType: Corner.Type) {
        when (cornerType) {
            Corner.Type.CUT -> {
                lineTo(shapeSize.width, cornerSize)
            }
            Corner.Type.ROUND -> {
                arcTo(
                    rect = Rect(
                        Offset(shapeSize.width - cornerSize, cornerSize),
                        square(cornerSize)
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            Corner.Type.NONE -> {
                lineTo(shapeSize.width, 0f)
            }
        }
    }

    private fun Path.bottomStart(shapeSize: Size, cornerSize: Float, cornerType: Corner.Type) {
        when (cornerType) {
            Corner.Type.CUT -> {
                lineTo(0f, shapeSize.height - cornerSize)
            }
            Corner.Type.ROUND -> {
                arcTo(
                    rect = Rect(
                        Offset(0f, shapeSize.height - cornerSize),
                        square(cornerSize)
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            Corner.Type.NONE -> {
                lineTo(0f, shapeSize.height)
            }
        }
    }

    private fun Path.bottomEnd(shapeSize: Size, cornerSize: Float, cornerType: Corner.Type) {
        when (cornerType) {
            Corner.Type.CUT -> {
                lineTo(shapeSize.width - cornerSize, shapeSize.height)
            }
            Corner.Type.ROUND -> {
                arcTo(
                    rect = Rect(
                        Offset(shapeSize.width - cornerSize, shapeSize.height - cornerSize),
                        square(cornerSize)
                    ),
                    startAngleDegrees = 0f,
                    sweepAngleDegrees = 90f,
                    forceMoveTo = false
                )
            }
            Corner.Type.NONE -> {
                lineTo(shapeSize.width, shapeSize.height)
            }
        }
    }

    private fun square(size: Float) = Size(size, size)
}

@Preview
@Composable
fun ShapePreview() {
    val shape = CornerShapeable(
        corners = ShapeCorners(
            topStart = Corner(16.dp, Corner.Type.CUT),
            topEnd = Corner(16.dp, Corner.Type.CUT),
            bottomStart = Corner(16.dp, Corner.Type.ROUND),
            bottomEnd = Corner(16.dp, Corner.Type.ROUND),
        )
    )

    Card(
        shape = shape,
        backgroundColor = Color.White,
        modifier = Modifier.size(200.dp, 100.dp)
    ) {

    }
}