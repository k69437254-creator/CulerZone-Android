
package com.culerzone.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

private data class ZoneTheme(
    val name: String,
    val bg: Color,
    val panel: Color,
    val primary: Color,
    val accent: Color,
    val text: Color,
    val muted: Color
)

private val zoneThemes = listOf(
    ZoneTheme(
        "برشلونة الليلي",
        Color(0xFF080D18), Color(0xFF131C2D),
        Color(0xFF1769C2), Color(0xFFA50044),
        Color(0xFFF4F6FC), Color(0xFF9AA8BD)
    ),
    ZoneTheme(
        "بلو غامق",
        Color(0xFF071626), Color(0xFF102943),
        Color(0xFF168BCE), Color(0xFF36C5F0),
        Color(0xFFF2F8FF), Color(0xFFA2BDD0)
    ),
    ZoneTheme(
        "أحمر وأسود",
        Color(0xFF130B10), Color(0xFF26131D),
        Color(0xFFA50044), Color(0xFFFF526F),
        Color(0xFFFFF2F5), Color(0xFFC6AAB5)
    ),
    ZoneTheme(
        "نهاري",
        Color(0xFFF0F3F8), Color(0xFFFFFFFF),
        Color(0xFF0755A5), Color(0xFFA50044),
        Color(0xFF172238), Color(0xFF66758C)
    ),
    ZoneTheme(
        "ذهبي",
        Color(0xFF11100A), Color(0xFF242114),
        Color(0xFFB58B19), Color(0xFFF5C518),
        Color(0xFFFFF9E8), Color(0xFFC5B98F)
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CulerZoneApp()
        }
    }
}

@Composable
private fun CulerZoneApp() {
    var themeIndex by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var showThemes by remember { mutableStateOf(false) }

    val t = zoneThemes[themeIndex]

    val scheme = lightColorScheme(
        primary = t.primary,
        secondary = t.accent,
        background = t.bg,
        surface = t.panel,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = t.text,
        onSurface = t.text
    )

    MaterialTheme(colorScheme = scheme) {
        Scaffold(
            containerColor = t.bg,
            bottomBar = {
                NavigationBar(
                    containerColor = t.panel,
                    contentColor = t.text
                ) {
                    val tabs = listOf(
                        "الرئيسية", "المباريات", "الفريق",
                        "المجتمع", "حسابي"
                    )
                    val icons = listOf("⌂", "⚽", "👥", "☷", "●")

                    tabs.forEachIndexed { index, title ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            icon = {
                                Text(icons[index], fontSize = 19.sp)
                            },
                            label = {
                                Text(title, fontSize = 10.sp)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = t.accent,
                                selectedTextColor = t.accent,
                                indicatorColor = t.primary.copy(alpha = 0.22f),
                                unselectedIconColor = t.muted,
                                unselectedTextColor = t.muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 24.dp
                ),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "CULERZONE",
                                color = t.accent,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                            Text(
                                pageTitle(selectedTab),
                                color = t.text,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = { showThemes = true },
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("🎨 الثيم", color = t.text)
                        }
                    }
                }

                when (selectedTab) {
                    0 -> {
                        item { HeroCard(t) }
                        item { SectionHeading("آخر الأخبار", t) }
                        item {
                            NewsItem(
                                "🔵",
                                "متابعة أخبار برشلونة",
                                "مساحة الأخبار جاهزة للربط بمصدر موثوق.",
                                t
                            )
                        }
                        item {
                            NewsItem(
                                "🔴",
                                "كل ما يخص البلوغرانا",
                                "ستظهر الأخبار الحقيقية بعد إعداد مصدر البيانات.",
                                t
                            )
                        }
                        item { SectionHeading("مركز المباراة", t) }
                        item { MatchCard(t) }
                        item { SectionHeading("وصول سريع", t) }
                        item {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                QuickCard(
                                    "⚽", "المباريات", t,
                                    Modifier.weight(1f)
                                ) {
                                    selectedTab = 1
                                }
                                QuickCard(
                                    "👥", "الفريق", t,
                                    Modifier.weight(1f)
                                ) {
                                    selectedTab = 2
                                }
                                QuickCard(
                                    "☷", "المجتمع", t,
                                    Modifier.weight(1f)
                                ) {
                                    selectedTab = 3
                                }
                            }
                        }
                    }

                    1 -> {
                        item { SectionHeading("مركز المباريات", t) }
                        item { MatchCard(t) }
                        item {
                            InfoCard(
                                "ملاحظة",
                                "المواعيد والنتائج المعروضة هنا نموذج أولي وليست بيانات مباشرة.",
                                t
                            )
                        }
                        item { SectionHeading("التنبيهات", t) }
                        item {
                            InfoCard(
                                "قريبًا",
                                "إضافة جدول مباريات برشلونة مع النتائج والتوقيت بعد ربط مصدر بيانات حقيقي.",
                                t
                            )
                        }
                    }

                    2 -> {
                        item { SectionHeading("الفريق الأول", t) }
                        item {
                            InfoCard(
                                "FC Barcelona",
                                "قائمة اللاعبين هنا نسخة أولية. سنربطها بمصدر محدث لتفادي عرض معلومات قديمة.",
                                t
                            )
                        }
                        item { SectionHeading("خطوط الفريق", t) }
                        item { PlayerLine("🧤", "حراس المرمى", "GK", t) }
                        item { PlayerLine("🛡️", "خط الدفاع", "DEF", t) }
                        item { PlayerLine("🎯", "خط الوسط", "MID", t) }
                        item { PlayerLine("⚡", "خط الهجوم", "FWD", t) }
                    }

                    3 -> {
                        item {
                            HeroCard(
                                t,
                                "مجتمع الكوليز",
                                "مساحة النقاشات والتوقعات بين مشجعي برشلونة."
                            )
                        }
                        item {
                            InfoCard(
                                "المجتمع غير متصل بعد",
                                "المنشورات والمناقشات تحتاج إلى ربط إضافي بقاعدة البيانات.",
                                t
                            )
                        }
                        item { SectionHeading("أفكار للمجتمع", t) }
                        item {
                            NewsItem(
                                "💬",
                                "نقاشات المباريات",
                                "ميزة النقاشات تحتاج إلى تجهيز قاعدة بيانات المنشورات.",
                                t
                            )
                        }
                        item {
                            NewsItem(
                                "📊",
                                "توقع النتيجة",
                                "ميزة قادمة بعد تجهيز التخزين والمزامنة.",
                                t
                            )
                        }
                    }

                    else -> {
                        item {
                            HeroCard(
                                t,
                                "حسابي",
                                "خصّص تجربتك داخل CulerZone."
                            )
                        }
                        item { AuthPanel(t) }
                        item { SectionHeading("التخصيص", t) }
                        item { NewsItem("🎨", "الثيم الحالي", t.name, t) }
                        item {
                            Button(
                                onClick = { showThemes = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(14.dp)
                            ) {
                                Text("اختيار ثيم التطبيق")
                            }
                        }
                        item {
                            InfoCard(
                                "عن التطبيق",
                                "CulerZone — تطبيق عربي لمشجعي برشلونة. هذه نسخة أولية قابلة للتطوير.",
                                t
                            )
                        }
                    }
                }
            }
        }

        if (showThemes) {
            AlertDialog(
                onDismissRequest = { showThemes = false },
                containerColor = t.panel,
                title = {
                    Text(
                        "اختار مظهر التطبيق",
                        color = t.text,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        zoneThemes.forEachIndexed { index, option ->
                            OutlinedButton(
                                onClick = {
                                    themeIndex = index
                                    showThemes = false
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = t.text
                                )
                            ) {
                                Box(
                                    Modifier.weight(1f),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Text(
                                        option.name,
                                        fontWeight = if (themeIndex == index) {
                                            FontWeight.Bold
                                        } else {
                                            FontWeight.Normal
                                        }
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        Modifier.size(18.dp).background(
                                            option.primary,
                                            RoundedCornerShape(5.dp)
                                        )
                                    )
                                    Box(
                                        Modifier.size(18.dp).background(
                                            option.accent,
                                            RoundedCornerShape(5.dp)
                                        )
                                    )
                                }
                                if (themeIndex == index) {
                                    Text(" ✓", color = t.accent)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showThemes = false }) {
                        Text("إغلاق")
                    }
                }
            )
        }
    }
}

private fun pageTitle(tab: Int) =
    listOf("عالم برشلونة", "المباريات", "الفريق", "المجتمع", "حسابي")[tab]

@Composable
private fun HeroCard(
    t: ZoneTheme,
    title: String = "أكثر من مجرد نادٍ",
    subtitle: String = "كل أخبار البلوغرانا، في مكان واحد."
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = t.primary)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "🔵🔴  FC BARCELONA",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                title,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                subtitle,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
            Surface(
                color = Color.White.copy(alpha = 0.16f),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    "MÉS QUE UN CLUB",
                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 7.dp
                    ),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun SectionHeading(title: String, t: ZoneTheme) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier.width(4.dp).height(22.dp).background(
                t.accent,
                RoundedCornerShape(4.dp)
            )
        )
        Spacer(Modifier.width(9.dp))
        Text(
            title,
            color = t.text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun NewsItem(
    emoji: String,
    title: String,
    description: String,
    t: ZoneTheme
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(48.dp).background(
                    t.primary.copy(alpha = 0.2f),
                    RoundedCornerShape(14.dp)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(emoji, fontSize = 22.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    title,
                    color = t.text,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    description,
                    color = t.muted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun MatchCard(t: ZoneTheme) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "مباراة نموذجية • بيانات غير مباشرة",
                color = t.muted,
                fontSize = 11.sp
            )

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔵🔴", fontSize = 28.sp)
                    Text(
                        "برشلونة",
                        color = t.text,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    "VS",
                    color = t.accent,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⚪", fontSize = 28.sp)
                    Text(
                        "الفريق المنافس",
                        color = t.text,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(color = t.muted.copy(alpha = 0.2f))

            Text(
                "سيظهر الموعد والملعب بعد ربط جدول مباريات موثوق.",
                color = t.muted,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    description: String,
    t: ZoneTheme
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                title,
                color = t.text,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                description,
                color = t.muted,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun QuickCard(
    emoji: String,
    label: String,
    t: ZoneTheme,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(
                vertical = 16.dp,
                horizontal = 6.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(emoji, fontSize = 22.sp)
            Text(
                label,
                color = t.text,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun PlayerLine(
    emoji: String,
    title: String,
    code: String,
    t: ZoneTheme
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(Modifier.width(12.dp))
            Text(
                title,
                modifier = Modifier.weight(1f),
                color = t.text,
                fontWeight = FontWeight.Bold
            )
            Text(
                code,
                color = t.accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Composable
private fun AuthPanel(t: ZoneTheme) {
    val auth = remember { FirebaseAuth.getInstance() }
    val database = remember { FirebaseDatabase.getInstance().reference }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isRegister by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var busy by remember { mutableStateOf(false) }
    var loadingProfile by remember { mutableStateOf(false) }

    var signedIn by remember {
        mutableStateOf(auth.currentUser != null)
    }

    var currentEmail by remember {
        mutableStateOf(auth.currentUser?.email.orEmpty())
    }

    var displayName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    LaunchedEffect(signedIn) {
        val user = auth.currentUser

        if (signedIn && user != null) {
            currentEmail = user.email.orEmpty()
            loadingProfile = true

            database.child("users")
                .child(user.uid)
                .get()
                .addOnSuccessListener { snapshot ->
                    displayName = snapshot.child("displayName")
                        .getValue(String::class.java).orEmpty()

                    username = snapshot.child("username")
                        .getValue(String::class.java).orEmpty()

                    loadingProfile = false
                }
                .addOnFailureListener { error ->
                    loadingProfile = false
                    message = "تعذّر تحميل الملف الشخصي: ${
                        error.localizedMessage ?: "تحقق من الاتصال"
                    }"
                }
        } else {
            displayName = ""
            username = ""
        }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = t.panel)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                if (signedIn) "حسابك في CulerZone"
                else if (isRegister) "إنشاء حساب جديد"
                else "تسجيل الدخول",
                color = t.text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            if (signedIn) {
                Text("البريد الإلكتروني", color = t.muted)
                Text(currentEmail, color = t.text)

                HorizontalDivider(
                    color = t.muted.copy(alpha = 0.2f)
                )

                Text(
                    "الملف الشخصي",
                    color = t.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "أدخل المعلومات التي تريد ظهورها في ملفك الشخصي.",
                    color = t.muted,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                OutlinedTextField(
                    value = displayName,
                    onValueChange = { displayName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("الاسم الظاهر") },
                    placeholder = { Text("مثال: مشجع برشلونة") },
                    singleLine = true,
                    enabled = !busy && !loadingProfile
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = {
                        username = it
                            .trimStart()
                            .replace(" ", "")
                            .removePrefix("@")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("اسم المستخدم") },
                    placeholder = { Text("مثال: culer123") },
                    singleLine = true,
                    enabled = !busy && !loadingProfile
                )

                if (loadingProfile) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(22.dp),
                            color = t.accent,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "جارٍ تحميل الملف الشخصي...",
                            color = t.muted
                        )
                    }
                }

                Button(
                    onClick = {
                        val cleanName = displayName.trim()
                        val cleanUsername = username.trim()
                            .removePrefix("@")
                            .lowercase()

                        when {
                            cleanName.length < 2 -> {
                                message = "اكتب اسمًا ظاهرًا من حرفين على الأقل"
                            }

                            cleanUsername.length < 3 -> {
                                message = "اسم المستخدم يجب أن يحتوي على 3 أحرف على الأقل"
                            }

                            !cleanUsername.matches(
                                Regex("[a-z0-9_.]+")
                            ) -> {
                                message = "اسم المستخدم يقبل الإنجليزية والأرقام والنقطة والشرطة السفلية فقط"
                            }

                            else -> {
                                val user = auth.currentUser

                                if (user == null) {
                                    signedIn = false
                                    message = "سجّل الدخول مجددًا"
                                } else {
                                    busy = true
                                    message = ""

                                    val profile = mapOf(
                                        "displayName" to cleanName,
                                        "username" to cleanUsername
                                    )

                                    database.child("users")
                                        .child(user.uid)
                                        .updateChildren(profile)
                                        .addOnSuccessListener {
                                            busy = false
                                            displayName = cleanName
                                            username = cleanUsername
                                            message = "تم حفظ الملف الشخصي بنجاح"
                                        }
                                        .addOnFailureListener { error ->
                                            busy = false
                                            message = "فشل الحفظ: ${
                                                error.localizedMessage
                                                    ?: "تحقق من الاتصال وقواعد Firebase"
                                            }"
                                        }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !busy && !loadingProfile,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (busy) "جارٍ الحفظ..."
                        else "حفظ الملف الشخصي"
                    )
                }

                OutlinedButton(
                    onClick = {
                        auth.signOut()
                        signedIn = false
                        currentEmail = ""
                        email = ""
                        password = ""
                        message = "تم تسجيل الخروج"
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !busy,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("تسجيل الخروج")
                }
            } else {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("البريد الإلكتروني") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("كلمة المرور") },
                    singleLine = true
                )

                Button(
                    onClick = {
                        val cleanEmail = email.trim()

                        if (
                            cleanEmail.isBlank() ||
                            password.length < 6
                        ) {
                            message =
                                "أدخل بريدًا صحيحًا وكلمة مرور من 6 أحرف على الأقل"
                        } else {
                            busy = true
                            message = ""

                            val task = if (isRegister) {
                                auth.createUserWithEmailAndPassword(
                                    cleanEmail,
                                    password
                                )
                            } else {
                                auth.signInWithEmailAndPassword(
                                    cleanEmail,
                                    password
                                )
                            }

                            task.addOnCompleteListener { result ->
                                busy = false

                                if (result.isSuccessful) {
                                    signedIn = true
                                    currentEmail =
                                        auth.currentUser?.email.orEmpty()

                                    message = if (isRegister) {
                                        "تم إنشاء الحساب بنجاح. أكمل بيانات ملفك الشخصي."
                                    } else {
                                        "تم تسجيل الدخول بنجاح"
                                    }
                                } else {
                                    message =
                                        result.exception?.localizedMessage
                                            ?: "تعذّر إكمال العملية. تحقق من البيانات والاتصال."
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !busy,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        if (busy) "جارٍ التنفيذ..."
                        else if (isRegister) "إنشاء الحساب"
                        else "دخول"
                    )
                }

                TextButton(
                    onClick = {
                        isRegister = !isRegister
                        message = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (isRegister) {
                            "عندي حساب — تسجيل الدخول"
                        } else {
                            "ما عندي حساب — إنشاء حساب"
                        }
                    )
                }
            }

            if (message.isNotBlank()) {
                Text(
                    message,
                    color = if (
                        message.contains("بنجاح") ||
                        message.contains("تم حفظ") ||
                        message.contains("تم تسجيل الخروج")
                    ) {
                        t.accent
                    } else {
                        t.muted
                    },
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )
            }
        }
    }
}
