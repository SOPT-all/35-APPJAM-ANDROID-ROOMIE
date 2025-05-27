package com.wearerommies.roomie.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.wearerommies.roomie.R

val PretendardBold = FontFamily(Font(R.font.pretendard_bold, FontWeight.Bold))
val PretendardSemiBold = FontFamily(Font(R.font.pretendard_semibold, FontWeight.SemiBold))
val PretendardMedium = FontFamily(Font(R.font.pretendard_medium, FontWeight.Medium))
val PretendardRegular = FontFamily(Font(R.font.pretendard_regular, FontWeight.Normal))

@Stable
class RoomieTypography internal constructor(
    heading1B20: TextStyle,
    heading2Sb20: TextStyle,
    heading3M20: TextStyle,
    heading4B18: TextStyle,
    heading5Sb18: TextStyle,
    title1R16: TextStyle,
    title2Sb16: TextStyle,
    title3M16: TextStyle,
    body1R14: TextStyle,
    body2Sb14: TextStyle,
    body3M14: TextStyle,
    body4R12: TextStyle,
    body5Sb12: TextStyle,
    body6M12: TextStyle,
    caption1R10: TextStyle,
    caption2Sb10: TextStyle,
    caption3M10: TextStyle,
    obdTitle1Sb24: TextStyle,
    obdTitle2Sb22: TextStyle,
    obdBody1M16: TextStyle
) {
    var heading1B20: TextStyle by mutableStateOf(heading1B20)
        private set
    var heading2Sb20: TextStyle by mutableStateOf(heading2Sb20)
        private set
    var heading3M20: TextStyle by mutableStateOf(heading3M20)
        private set
    var heading4B18: TextStyle by mutableStateOf(heading4B18)
        private set
    var heading5Sb18: TextStyle by mutableStateOf(heading5Sb18)
        private set
    var title1R16: TextStyle by mutableStateOf(title1R16)
        private set
    var title2Sb16: TextStyle by mutableStateOf(title2Sb16)
        private set
    var title3M16: TextStyle by mutableStateOf(title3M16)
        private set
    var body1R14: TextStyle by mutableStateOf(body1R14)
        private set
    var body2Sb14: TextStyle by mutableStateOf(body2Sb14)
        private set
    var body3M14: TextStyle by mutableStateOf(body3M14)
        private set
    var body4R12: TextStyle by mutableStateOf(body4R12)
        private set
    var body5Sb12: TextStyle by mutableStateOf(body5Sb12)
        private set
    var body6M12: TextStyle by mutableStateOf(body6M12)
        private set
    var caption1R10: TextStyle by mutableStateOf(caption1R10)
        private set
    var caption2Sb10: TextStyle by mutableStateOf(caption2Sb10)
        private set
    var caption3M10: TextStyle by mutableStateOf(caption3M10)
        private set
    var obdTitle1Sb24: TextStyle by mutableStateOf(obdTitle1Sb24)
        private set
    var obdTitle2Sb22: TextStyle by mutableStateOf(obdTitle2Sb22)
        private set
    var obdBody1M16: TextStyle by mutableStateOf(obdBody1M16)
        private set

    fun copy(
        heading1B20: TextStyle = this.heading1B20,
        heading2Sb20: TextStyle = this.heading2Sb20,
        heading3M20: TextStyle = this.heading3M20,
        heading4B18: TextStyle = this.heading4B18,
        heading5Sb18: TextStyle = this.heading5Sb18,
        title1R16: TextStyle = this.title1R16,
        title2Sb16: TextStyle = this.title2Sb16,
        title3M16: TextStyle = this.title3M16,
        body1R14: TextStyle = this.body1R14,
        body2Sb14: TextStyle = this.body2Sb14,
        body3M14: TextStyle = this.body3M14,
        body4R12: TextStyle = this.body4R12,
        body5Sb12: TextStyle = this.body5Sb12,
        body6M12: TextStyle = this.body6M12,
        caption1R10: TextStyle = this.caption1R10,
        caption2Sb10: TextStyle = this.caption2Sb10,
        caption3M10: TextStyle = this.caption3M10,
        obdTitle1Sb24: TextStyle = this.obdTitle1Sb24,
        obdTitle2Sb22: TextStyle = this.obdTitle2Sb22,
        obdBody1M16: TextStyle = this.obdBody1M16
    ): RoomieTypography = RoomieTypography(
        heading1B20,
        heading2Sb20,
        heading3M20,
        heading4B18,
        heading5Sb18,
        title1R16,
        title2Sb16,
        title3M16,
        body1R14,
        body2Sb14,
        body3M14,
        body4R12,
        body5Sb12,
        body6M12,
        caption1R10,
        caption2Sb10,
        caption3M10,
        obdTitle1Sb24,
        obdTitle2Sb22,
        obdBody1M16
    )

    fun update(other: RoomieTypography) {
        heading1B20 = other.heading1B20
        heading2Sb20 = other.heading2Sb20
        heading3M20 = other.heading3M20
        heading4B18 = other.heading4B18
        heading5Sb18 = other.heading5Sb18
        title1R16 = other.title1R16
        title2Sb16 = other.title2Sb16
        title3M16 = other.title3M16
        body1R14 = other.body1R14
        body2Sb14 = other.body2Sb14
        body3M14 = other.body3M14
        body4R12 = other.body4R12
        body5Sb12 = other.body5Sb12
        body6M12 = other.body6M12
        caption1R10 = other.caption1R10
        caption2Sb10 = other.caption2Sb10
        caption3M10 = other.caption3M10
        obdTitle1Sb24 = other.obdTitle1Sb24
        obdTitle2Sb22 = other.obdTitle2Sb22
        obdBody1M16 = other.obdBody1M16
    }
}

fun roomieTextStyle(
    fontFamily: FontFamily,
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit,
    letterSpacing: TextUnit
): TextStyle = TextStyle(
    fontFamily = fontFamily,
    fontWeight = fontWeight,
    fontSize = fontSize,
    lineHeight = lineHeight,
    letterSpacing = letterSpacing,
    lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )
)

@Composable
fun RoomieTypography(): RoomieTypography {
    return RoomieTypography(
        heading1B20 = roomieTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.005).em
        ),
        heading2Sb20 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.005).em
        ),
        heading3M20 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = (-0.005).em
        ),
        heading4B18 = roomieTextStyle(
            fontFamily = PretendardBold,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.005).em
        ),
        heading5Sb18 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.005).em
        ),
        title1R16 = roomieTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.01).em
        ),
        title2Sb16 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.01).em
        ),
        title3M16 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.01).em
        ),
        body1R14 = roomieTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.01).em
        ),
        body2Sb14 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.01).em
        ),
        body3M14 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = (-0.01).em
        ),
        body4R12 = roomieTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.01).em
        ),
        body5Sb12 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.01).em
        ),
        body6M12 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.01).em
        ),
        caption1R10 = roomieTextStyle(
            fontFamily = PretendardRegular,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = (-0.01).em
        ),
        caption2Sb10 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = (-0.01).em
        ),
        caption3M10 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            lineHeight = 14.sp,
            letterSpacing = (-0.01).em
        ),
        obdTitle1Sb24 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.033).sp
        ),
        obdTitle2Sb22 = roomieTextStyle(
            fontFamily = PretendardSemiBold,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 30.sp,
            letterSpacing = (-0.033).sp
        ),
        obdBody1M16 = roomieTextStyle(
            fontFamily = PretendardMedium,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = (-0.033).sp
        )
    )
}
