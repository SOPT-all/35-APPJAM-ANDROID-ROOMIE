package com.wearerommies.roomie.presentation.ui.mypage.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wearerommies.roomie.R
import com.wearerommies.roomie.presentation.core.component.RoomieTopBar
import com.wearerommies.roomie.presentation.core.extension.bottomBorder
import com.wearerommies.roomie.presentation.core.extension.noRippleClickable
import com.wearerommies.roomie.presentation.core.util.convertDpToFloat
import com.wearerommies.roomie.ui.theme.RoomieAndroidTheme
import com.wearerommies.roomie.ui.theme.RoomieTheme

@Composable
fun MyTopBar(
    navigateUp: () -> Unit,
    @StringRes title: Int,
) {
    RoomieTopBar(
        modifier = Modifier
            .bottomBorder(
                height = convertDpToFloat(1.dp),
                color = RoomieTheme.colors.grayScale4
            ),
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .noRippleClickable { navigateUp() }
                    .padding(all = 10.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_line_black_24px),
                contentDescription = stringResource(R.string.move_back)
            )
        },
        title = stringResource(title)
    )
}

@Preview
@Composable
private fun MyTopBarPreview() {
    RoomieAndroidTheme {
        Column {
            MyTopBar(
                navigateUp = {},
                title = R.string.birth_edit
            )
        }
    }
}