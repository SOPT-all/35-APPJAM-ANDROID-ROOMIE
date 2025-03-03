package com.wearerommies.roomie.presentation.core.extension

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) { onClick() }
}

fun Modifier.roomieButtonClickable(
    onClick: () -> Unit,
    pressedColor: Color = Color.Transparent,
    isPressed: Boolean = false,
): Modifier = composed {
    this
        .clickable(
            indication = if (isPressed) ripple(color = pressedColor) else null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
}

fun Modifier.topBorder(
    height: Float,
    color: Color
) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = height,
    )
}

fun Modifier.bottomBorder(
    color: Color,
    height: Float,
) = this.drawWithContent {
    drawContent()
    drawLine(
        color = color,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = height,
    )
}

fun Modifier.customShadow(
    elevation: Dp = 4.dp,
    shape: Shape = RectangleShape,
    spotColor: Color = Color(0x80000000),
    ambientColor: Color = Color(0x80000000),
    clip: Boolean = false
): Modifier = this.shadow(
    elevation = elevation,
    shape = shape,
    clip = clip,
    spotColor = spotColor,
    ambientColor = ambientColor
)

fun Modifier.roundedBackgroundWithBorder(
    cornerRadius: Dp,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 0.dp,
): Modifier {
    return this
        .clip(RoundedCornerShape(cornerRadius))
        .background(backgroundColor, shape = RoundedCornerShape(size = cornerRadius))
        .border(
            width = borderWidth,
            color = borderColor,
            shape = RoundedCornerShape(cornerRadius)
        )
}

fun Modifier.showIf(condition: Boolean): Modifier {
    return if (condition) this else Modifier.size(0.dp)
}
