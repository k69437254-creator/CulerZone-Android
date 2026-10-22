
package com.culerzone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
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
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFF101727)
                ) {
                    val tabs = listOf(
                        "الرئيسية" to "⌂",
                        "المباريات" to "⚽",
                        "الفريق" to "👥",
                        "المجتمع" to "💬",
                        "حسابي" to "●"
                    )

                    tabs.forEachIndexed { index, tab ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                Text(tab.second, fontSize = 20.sp)
                            },
                            label = {
                                Text(tab.first, fontSize = 10.sp)
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
                item { Header() }

                when (selectedTab) {
                    0 -> {
                        item { WelcomeCard() }
                        item { SectionTitle("مركز المشجع") }
                        item {
                            InfoCard(
                                "المباريات",
                                "تابع جدول برشلونة",
                                BarcaBlue
                            )
                        }
                        item {
                            InfoCard(
                                "الفريق",
                                "اكتشف مراكز اللاعبين",
                                BarcaRed
                            )
                        }
                        item { SectionTitle("عالم البلوغرانا") }
                        item {
                            NewsCard(
                                "🔵🔴",
                                "أخبار برشلونة",
                                "سيتم عرض الأخبار بعد ربط مصدر موثوق."
                            )
                        }
                    }

                    1 -> {
                        item { SectionTitle("مباريات برشلونة") }
                        item { MatchCenter() }
                    }

                    2 -> {
                        item { SectionTitle("فريق برشلونة") }
                        item { TeamCenter() }
                    }

                    3 -> {
                        item { SectionTitle("مجتمع CulerZone") }
                        item {
                            NewsCard(
                                "💬",
                                "مجتمع الكتلان",
                                "واجهة المجتمع جاهزة للتطوير. المنشورات والمزامنة تحتاج خدمة خلفية."
                            )
                        }
                    }

                    else -> {
                        item { SectionTitle("حسابي") }
                        item {
                            NewsCard(
                                "👤",
                                "ملف المشجع",
                                "تسجيل الدخول وحفظ الحساب يحتاجان إلى إعداد خدمة حسابات."
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
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
                fontSize = 24.sp,
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
                .size(50.dp)
                .background(
                    Brush.linearGradient(
                        listOf(BarcaBlue, BarcaRed)
                    ),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "FCB",
                color = TextWhite,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun WelcomeCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF073C78), Color(0xFF63102F))
                ),
                RoundedCornerShape(24.dp)
            )
            .padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            "🔵 🔴 MES QUE UN CLUB",
            color = Gold,
            fontSize = 12.sp
        )
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
        fontSize = 20.sp,
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
                .size(52.dp)
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
fun InfoCard(
    title: String,
    description: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(18.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            Modifier
                .width(34.dp)
                .height(4.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Text(
            title,
            color = TextWhite,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            description,
            color = TextMuted,
            fontSize = 13.sp
        )
    }
}

@Composable
fun MatchCenter() {
    var selectedFilter by remember {
        mutableStateOf("القادمة")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "مركز المباريات",
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "تابع مباريات البلوغرانا",
            color = TextMuted,
            fontSize = 13.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("القادمة", "النتائج").forEach { filter ->
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = { Text(filter) }
                )
            }
        }

        Text(
            if (selectedFilter == "القادمة") {
                "المباريات القادمة"
            } else {
                "النتائج السابقة"
            },
            color = Gold,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "ستظهر المواعيد والنتائج بعد ربط مصدر بيانات حقيقي.",
            color = TextMuted,
            fontSize = 13.sp,
            lineHeight = 21.sp
        )
    }
}

private data class DemoPlayer(
    val name: String,
    val position: String,
    val number: String
)

@Composable
fun TeamCenter() {
    var selectedPosition by remember {
        mutableStateOf("الكل")
    }

    val positions = listOf(
        "الكل",
        "حراس",
        "دفاع",
        "وسط",
        "هجوم"
    )

    val demoPlayers = listOf(
        DemoPlayer("حارس تجريبي 1", "حراس", "GK"),
        DemoPlayer("مدافع تجريبي 1", "دفاع", "DF"),
        DemoPlayer("مدافع تجريبي 2", "دفاع", "DF"),
        DemoPlayer("لاعب وسط تجريبي 1", "وسط", "MF"),
        DemoPlayer("لاعب وسط تجريبي 2", "وسط", "MF"),
        DemoPlayer("مهاجم تجريبي 1", "هجوم", "FW"),
        DemoPlayer("مهاجم تجريبي 2", "هجوم", "FW")
    )

    val filteredPlayers = if (selectedPosition == "الكل") {
        demoPlayers
    } else {
        demoPlayers.filter {
            it.position == selectedPosition
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(20.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "تشكيلة الفريق",
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "واجهة تجريبية — أسماء اللاعبين الحقيقية ستُضاف بعد تحديث بيانات الفريق.",
            color = TextMuted,
            fontSize = 12.sp,
            lineHeight = 19.sp
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            positions.chunked(3).forEach { rowPositions ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    rowPositions.forEach { position ->
                        FilterChip(
                            modifier = Modifier.weight(1f),
                            selected = selectedPosition == position,
                            onClick = {
                                selectedPosition = position
                            },
                            label = {
                                Text(
                                    position,
                                    fontSize = 11.sp,
                                    maxLines = 1
                                )
                            }
                        )
                    }
                    repeat(3 - rowPositions.size) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        Text(
            "عدد اللاعبين: ${filteredPlayers.size}",
            color = Gold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        filteredPlayers.forEach { player ->
            PlayerCard(player)
        }
    }
}

@Composable
private fun PlayerCard(player: DemoPlayer) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFF1A263B),
                RoundedCornerShape(14.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    Brush.linearGradient(
                        listOf(BarcaBlue, BarcaRed)
                    ),
                    RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                player.number,
                color = TextWhite,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                player.name,
                color = TextWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                player.position,
                color = TextMuted,
                fontSize = 12.sp
            )
        }
    }
}
