package com.akari.uicomponents.textFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

class AkariTextFieldPadding (
    var contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    var leadingIconPadding: PaddingValues = PaddingValues(start = 8.dp),
    var trailingIconPadding: PaddingValues = PaddingValues(end = 8.dp),
    var supportingTextPadding: PaddingValues = PaddingValues(start = 16.dp, end = 16.dp),
    var prefixPadding: PaddingValues = PaddingValues(start = 16.dp),
    var suffixPadding: PaddingValues = PaddingValues(end = 16.dp),
    var labelPadding: PaddingValues = PaddingValues(bottom = 4.dp),
    var mainContentPadding: PaddingValues = PaddingValues(all = 0.dp)
){
}