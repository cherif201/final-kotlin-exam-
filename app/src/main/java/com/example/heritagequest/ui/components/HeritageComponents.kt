package com.example.heritagequest.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Bolt
import androidx.compose.material.icons.outlined.Celebration
import androidx.compose.material.icons.outlined.Cottage
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Mosque
import androidx.compose.material.icons.outlined.Park
import androidx.compose.material.icons.outlined.Waves
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.heritagequest.domain.model.Category
import com.example.heritagequest.ui.theme.CoralAccent
import com.example.heritagequest.ui.theme.DividerTint
import com.example.heritagequest.ui.theme.HeritageGold
import com.example.heritagequest.ui.theme.HeritageGoldSoft
import com.example.heritagequest.ui.theme.HeritageTheme
import com.example.heritagequest.ui.theme.MediterraneanBlue
import com.example.heritagequest.ui.theme.MediterraneanBlueDark
import com.example.heritagequest.ui.theme.MediterraneanBlueLight
import com.example.heritagequest.ui.theme.OliveAccent
import com.example.heritagequest.ui.theme.WarmCream
import com.example.heritagequest.ui.theme.WarmSurfaceVariant

@Composable
fun HeritageScreenBackground(
    modifier: Modifier = Modifier,
    topBlueFraction: Float = 0.28f,
    bottomBand: Boolean = true,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(WarmCream)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(topBlueFraction)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(MediterraneanBlueLight, MediterraneanBlueDark)
                    )
                )
                .mediterraneanPattern()
        )
        if (bottomBand) {
            PatternBand(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
        content()
    }
}

fun Modifier.mediterraneanPattern(): Modifier = drawBehind {
    val lineColor = Color.White.copy(alpha = 0.05f)
    val step = 44.dp.toPx()
    var x = -size.height
    while (x < size.width + size.height) {
        drawLine(
            color = lineColor,
            start = Offset(x, 0f),
            end = Offset(x + size.height, size.height),
            strokeWidth = 1.dp.toPx()
        )
        x += step
    }
    var y = 0f
    while (y < size.height + size.width) {
        drawLine(
            color = lineColor,
            start = Offset(0f, y),
            end = Offset(size.width, y + size.width),
            strokeWidth = 1.dp.toPx()
        )
        y += step
    }
}

@Composable
fun PatternBand(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(34.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(MediterraneanBlueLight, MediterraneanBlue, MediterraneanBlueDark)
                )
            )
            .drawBehind {
                val stroke = 2.dp.toPx()
                val gold = HeritageGold.copy(alpha = 0.5f)
                drawLine(gold, Offset(0f, stroke), Offset(size.width, stroke), stroke)
                drawLine(gold, Offset(0f, size.height - stroke), Offset(size.width, size.height - stroke), stroke)
                val circleRadius = 5.dp.toPx()
                val gap = 52.dp.toPx()
                var cx = circleRadius * 2
                while (cx < size.width) {
                    drawCircle(gold, circleRadius, Offset(cx, size.height / 2))
                    cx += gap
                }
            }
    )
}

@Composable
fun HeritageHeaderPanel(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
    onAction: (() -> Unit)? = null
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(bottomStart = 34.dp, bottomEnd = 34.dp, topStart = 110.dp, topEnd = 110.dp),
            colors = CardDefaults.cardColors(containerColor = MediterraneanBlue)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .mediterraneanPattern()
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HeritageEmblem()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
                subtitle?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = HeritageGoldSoft,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OrnamentDivider()
            }
        }

        if (onBack != null) {
            HeritageRoundIconButton(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = "Back",
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 22.dp, top = 18.dp)
            )
        }

        if (actionIcon != null && onAction != null) {
            HeritageRoundIconButton(
                imageVector = actionIcon,
                contentDescription = "Action",
                onClick = onAction,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 22.dp, top = 18.dp)
            )
        }
    }
}

@Composable
fun HeritageRoundIconButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.size(54.dp),
        shape = CircleShape,
        color = Color.Transparent,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.4.dp, Color.White.copy(alpha = 0.6f))
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = Color.White
            )
        }
    }
}

@Composable
fun HeritageEmblem(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.size(64.dp),
        color = HeritageGold.copy(alpha = 0.15f),
        shape = CircleShape
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = HeritageGold,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}

@Composable
fun OrnamentDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        DividerLine(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.AutoAwesome,
            contentDescription = null,
            tint = HeritageGold,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(16.dp)
        )
        DividerLine(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun DividerLine(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .height(8.dp)
            .padding(horizontal = 4.dp)
    ) {
        drawLine(
            color = HeritageGold.copy(alpha = 0.75f),
            start = Offset(0f, size.height / 2),
            end = Offset(size.width, size.height / 2),
            strokeWidth = 1.6.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f)
        )
    }
}

@Composable
fun HeritagePrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(26.dp),
        color = MediterraneanBlue,
        shadowElevation = 10.dp,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(letterSpacing = 1.8.sp),
                color = Color.White
            )
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = HeritageGold
                )
            }
        }
    }
}

@Composable
fun HeritageMetricCard(
    title: String,
    value: String,
    caption: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = iconTint.copy(alpha = 0.12f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = iconTint, modifier = Modifier.size(28.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = value,
                style = MaterialTheme.typography.displayMedium.copy(fontSize = 32.sp),
                color = MediterraneanBlue
            )
            Text(text = caption, style = MaterialTheme.typography.bodyMedium, color = HeritageTheme.extraColors.mutedText)
        }
    }
}

fun categoryIcon(category: Category): ImageVector = when (category) {
    Category.ROMAN -> Icons.Outlined.AccountBalance
    Category.ISLAMIC -> Icons.Outlined.Mosque
    Category.PUNIC -> Icons.Outlined.Waves
    Category.MODERN -> Icons.Outlined.LocationCity
    Category.NATURE -> Icons.Outlined.Park
    Category.CITIES -> Icons.Outlined.Cottage
}

fun categoryAccent(category: Category): Color = when (category) {
    Category.ROMAN -> MediterraneanBlue
    Category.ISLAMIC -> HeritageGold
    Category.PUNIC -> CoralAccent
    Category.MODERN -> MediterraneanBlueLight
    Category.NATURE -> OliveAccent
    Category.CITIES -> Color(0xFFE06A2C)
}

fun statIcon(index: Int): ImageVector = when (index) {
    0 -> Icons.Outlined.AccountBalance
    1 -> Icons.Outlined.AutoAwesome
    else -> Icons.Outlined.Bolt
}

@Composable
fun HeroBanner(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(Color(0xFF7DB0DF), Color(0xFF1967A3), MediterraneanBlue)
                        )
                    )
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(Color.White.copy(alpha = 0.18f), size.minDimension * 0.22f, Offset(size.width * 0.82f, size.height * 0.28f))
                    drawCircle(Color(0xFF0F5B8F).copy(alpha = 0.38f), size.minDimension * 0.3f, Offset(size.width * 0.15f, size.height * 0.82f))
                }
                Icon(
                    imageVector = Icons.Outlined.Explore,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.92f),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 18.dp)
                        .size(72.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MediterraneanBlue)
                    .padding(18.dp)
            ) {
                Column {
                    Text(text = title, style = MaterialTheme.typography.headlineSmall, color = Color.White)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = subtitle, style = MaterialTheme.typography.bodyLarge, color = WarmCream)
                }
            }
        }
    }
}

@Composable
fun HeritageSectionCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            content = content
        )
    }
}

@Composable
fun HeritageCategoryProgress(progress: Float, accent: Color, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = { progress.coerceIn(0f, 1f) },
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(100.dp)),
        color = accent,
        trackColor = WarmSurfaceVariant
    )
}

@Composable
fun HeritageTag(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    containerColor: Color = MediterraneanBlue,
    contentColor: Color = Color.White
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = containerColor,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = contentColor, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = contentColor, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
fun HeritageOptionCard(
    letter: String,
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(
            if (selected) 2.dp else 1.dp,
            if (selected) MediterraneanBlue else DividerTint
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                color = if (selected) MediterraneanBlue else CoralAccent,
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = letter, color = Color.White, style = MaterialTheme.typography.titleLarge)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            if (selected) {
                Icon(
                    imageVector = Icons.Outlined.Celebration,
                    contentDescription = null,
                    tint = MediterraneanBlue
                )
            }
        }
    }
}

@Composable
fun HeritageInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    valueColor: Color = MediterraneanBlueDark
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = CircleShape,
            color = MediterraneanBlue.copy(alpha = 0.08f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = MediterraneanBlue, modifier = Modifier.size(22.dp))
            }
        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(text = label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium)
        Text(text = value, color = valueColor, style = MaterialTheme.typography.titleMedium)
    }
}
