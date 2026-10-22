
private data class DemoPlayer(
    val name: String,
    val position: String,
    val number: String
)

private val barcaPlayers = listOf(
    DemoPlayer("Joan García", "حراس", "1"),
    DemoPlayer("Wojciech Szczęsny", "حراس", "13"),
    DemoPlayer("Dominik Livaković", "حراس", "25"),

    DemoPlayer("João Cancelo", "دفاع", "2"),
    DemoPlayer("Alejandro Balde", "دفاع", "3"),
    DemoPlayer("Pau Cubarsí", "دفاع", "5"),
    DemoPlayer("Xavi Espart", "دفاع", "12"),
    DemoPlayer("Andreas Christensen", "دفاع", "15"),
    DemoPlayer("Gerard Martín", "دفاع", "18"),
    DemoPlayer("Jules Koundé", "دفاع", "23"),
    DemoPlayer("Eric García", "دفاع", "24"),

    DemoPlayer("Brian Fariñas", "وسط", "4"),
    DemoPlayer("Gavi", "وسط", "6"),
    DemoPlayer("Fermín López", "وسط", "7"),
    DemoPlayer("Pedri", "وسط", "8"),
    DemoPlayer("Rodri", "وسط", "16"),
    DemoPlayer("Dani Olmo", "وسط", "20"),
    DemoPlayer("Frenkie de Jong", "وسط", "21"),
    DemoPlayer("Marc Bernal", "وسط", "22"),

    DemoPlayer("Gabriel Jesus", "هجوم", "9"),
    DemoPlayer("Lamine Yamal", "هجوم", "10"),
    DemoPlayer("Raphinha", "هجوم", "11"),
    DemoPlayer("Karim Adeyemi", "هجوم", "14"),
    DemoPlayer("Anthony Gordon", "هجوم", "17"),
    DemoPlayer("Roony Bardghji", "هجوم", "19"),
    DemoPlayer("Jesse Bisiwu", "هجوم", "27"),
    DemoPlayer("Hamza Abdelkarim", "هجوم", "29")
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

    val filteredPlayers = if (selectedPosition == "الكل") {
        barcaPlayers
    } else {
        barcaPlayers.filter {
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
            "الفريق الأول",
            color = TextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            "برشلونة • موسم 2026–2027",
            color = TextMuted,
            fontSize = 13.sp
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
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        filteredPlayers.forEach { player ->
            PlayerCard(player)
        }

        Text(
            "الأسماء والأرقام حسب القائمة المنشورة للموسم. قد تتغير القائمة مع تحديثات التسجيل.",
            color = TextMuted,
            fontSize = 11.sp,
            lineHeight = 17.sp
        )
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
                .size(46.dp)
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
                fontSize = 17.sp,
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
                color = Gold,
                fontSize = 12.sp
            )
        }

        Text(
            "FCB",
            color = TextMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
