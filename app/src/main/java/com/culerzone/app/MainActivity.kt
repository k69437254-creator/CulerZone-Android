
package com.culerzone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF080D18)
private val SurfaceDark = Color(0xFF131C2D)
private val BarcaBlue = Color(0xFF0755A5)
private val BarcaRed = Color(0xFFA50044)
private val Gold = Color(0xFFF5C518)
private val TextWhite = Color(0xFFF4F6FC)
private val TextMuted = Color(0xFF9AA8BD)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = BarcaBlue,
                    secondary = BarcaRed,
                    background = Background,
                    surface = SurfaceDark
                )
            ) {
                CulerZoneApp()
            }
        }
    }
}

@Composable
fun CulerZoneApp() {
    var selectedTab by remember { mutableIntStateOf(0) }

    CompositionLocalProvider(
        LocalLayoutDirection provides androidx.compose.ui.unit.LayoutDirection.Rtl
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF101727),
                    contentColor = TextWhite
                ) {
                    val tabs = listOf(
                        "الرئيسية" to "⌂",
                        "المباريات" to "⚽",
                        "الفريق" to "👥",
                        "المجتمع" to "💬",
                        "حسابي" to "●"
                    )

                    tabs.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                Text(item.second, fontSize = 20.sp)
                            },
                            label = {
                                Text(item.first, fontSize = 10.sp)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Gold,
                                selectedTextColor = Gold,
                                indicatorColor = BarcaBlue,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Background)
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Header()
                }

                when (selectedTab) {
                    0 -> {
                        item { WelcomeCard() }
                        item { SectionTitle("آخر الأخبار") }
                        item {
                            NewsCard(
                                "🔵🔴",
                                "أخبار برشلونة",
                                "تابع آخر أخبار النادي وانتقالات اللاعبين."
                            )
                        }
                        item {
                            NewsCard(
                                "🏟️",
                                "عالم البلوغرانا",
                                "كل ما يهم مشجعي برشلونة في مكان واحد."
                            )
                        }
                        item { SectionTitle("مركز المشجع") }
                        item { QuickInfo() }
                    }

                    1 -> {
                        item { SectionTitle("المباريات") }
                        item {
                            NewsCard(
                                "⚽",
                                "جدول المباريات",
                                "سيتم ربط جدول المباريات والنتائج الحقيقية لاحقًا."
                            )
                        }
                    }

                    2 -> {
                        item { SectionTitle("الفريق الأول") }
                        item {
                            NewsCard(
                                "👥",
                                "تشكيلة برشلونة",
                                "سيُضاف اللاعبون والمراكز والإحصائيات لاحقًا."
                            )
                        }
                    }

                    3 -> {
                        item { SectionTitle("مجتمع CulerZone") }
                        item {
                            NewsCard(
                                "💬",
                                "التقِ بمشجعي برشلونة",
                                "ستتوفر المنشورات والتعليقات والحسابات بعد ربط Firebase."
                            )
                        }
                    }

                    else -> {
                        item { SectionTitle("حسابي") }
                        item {
                            NewsCard(
                                "👤",
                                "ملف المشجع",
                                "سيُضاف إنشاء الحساب وتسجيل الدخول في مرحلة لاحقة."
                            )
                        }
                    }
                }

                item {
                    Text(
                        "CulerZone • نسخة تجريبية",
                        color = TextMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                "CULERZONE",
                color = TextWhite,
                fontSize = 23.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                "عالم برشلونة بين إيديك",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    androidx.compose.ui.graphics.Brush.linearGradient(
                        listOf(BarcaBlue, BarcaRed)
                    ),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("FCB", color = TextWhite, fontWeight = FontWeight.Black)
        }
    }
}

@Composable
fun WelcomeCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                androidx.compose.ui.graphics.Brush.linearGradient(
                    listOf(Color(0xFF073C78), Color(0xFF63102F))
                ),
                RoundedCornerShape(24.dp)
            )
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("🔵 🔴 MES QUE UN CLUB", color = Gold, fontSize = 12.sp)
        Text(
            "أهلًا بك بعالم الكتلان",
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "أخبار، مباريات، الفريق ومجتمع يجمع مشجعي برشلونة.",
            color = TextWhite,
            fontSize = 14.sp,
            lineHeight = 22.sp
        )
        Text(
            "FORÇA BARÇA",
            color = Gold,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        title,
        color = TextWhite,
        fontSize = 19.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun NewsCard(
    emoji: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(54.dp)
                .background(
                    BarcaBlue.copy(alpha = 0.3f),
                    RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(emoji, fontSize = 24.sp)
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                title,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                description,
                color = TextMuted,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun QuickInfo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        InfoCard("الأخبار", "قريبًا", BarcaBlue, Modifier.weight(1f))
        InfoCard("المجتمع", "قريبًا", BarcaRed, Modifier.weight(1f))
    }
}

@Composable
fun InfoCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(SurfaceDark, RoundedCornerShape(16.dp))
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            Modifier
                .width(30.dp)
                .height(4.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(title, color = TextMuted, fontSize = 12.sp)
        Text(value, color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}
