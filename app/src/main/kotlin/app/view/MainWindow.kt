package app.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.rememberWindowState
import binarysearchtrees.BinarySearchTree
import binarysearchtrees.splaytree.SplayTree
import java.awt.Dimension

@OptIn(ExperimentalTextApi::class)
@Composable
fun MainWindow(
    tree: BinarySearchTree<String, Position>,
    treeName: String,
    onCloseRequest: () -> Unit,
    title: String = "Splay",
    icon: Painter? = painterResource("treeIcon.png"),
    state: WindowState = rememberWindowState(
        position = WindowPosition(alignment = Alignment.Center),
        size = DpSize(1100.dp, 750.dp),
    )
) {
    Window(
        onCloseRequest = onCloseRequest,
        title = title,
        icon = icon,
        state = state
    ) {
        window.minimumSize = Dimension(800, 600)
        Box(Modifier.fillMaxSize().background(defaultBackground).padding(defaultPadding)) {
            val scrollDelta = defaultScrollDelta
            val indicator = mutableStateOf(0)

            Row(Modifier.fillMaxSize()) {
                Box(Modifier.width(300.dp).fillMaxHeight()) {
                    Column(Modifier.fillMaxSize()) {
                        Box(
                            Modifier.height(290.dp).fillMaxWidth().padding(defaultPadding)
                                .background(
                                    color = defaultBackground,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    2.dp,
                                    defaultBrush,
                                    RoundedCornerShape(10.dp)
                                )
                        ) {
                            Image(
                                painter = painterResource("treeIcon.png"),
                                contentDescription = "Logotype",
                                modifier = Modifier.height(200.dp)
                                    .width(400.dp)
                                    .padding(50.dp)
                            )

                            Text(
                                "Splay-T", fontSize = 36.sp,
                                modifier = Modifier.padding(top = 175.dp).width(300.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                "GryaznovAS", fontSize = 19.sp,
                                modifier = Modifier.padding(top = 215.dp).width(300.dp),
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Box(
                            Modifier.fillMaxSize().padding(defaultPadding)
                                .background(
                                    color = defaultBackground,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .border(
                                    2.dp,
                                    defaultBrush,
                                    RoundedCornerShape(10.dp)
                                )
                        ) {
                            Panel(
                                tree,
                                indicator,
                                treeName,
                                scrollDelta,
                                Modifier.fillMaxSize()
                                    .padding(defaultPadding)
                                    .background(
                                        color = defaultBackground,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                            )
                        }
                    }
                }
                Box(Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.padding(defaultPadding),
                        border = BorderStroke(2.dp, defaultVVBrush),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        TreeView(tree, indicator, defaultVertexSize, scrollDelta)
                    }
                }
            }
        }
    }
}

@Composable
fun Panel(
    tree: BinarySearchTree<String, Position>,
    indicator: MutableState<Int>,
    treeName: String,
    scrollDelta: ScrollDelta,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
    ) {
        val stateVertical = rememberScrollState(0)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(stateVertical)
                .padding(defaultPadding * 2)
        ) {
            Column(
            ) {

                TreeButton("Add") {
                    if (tree.find(it) == null) {
                        tree.insert(it, Position(0.dp, 0.dp))
                        setTreePositions(tree, defaultVertexSize, DpOffset(10.dp, 10.dp))
                        indicator.value = (indicator.value + 1) % 10
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                TreeButton("Del") {
                    val pos = tree.remove(it)
                    if (pos != null) {
                        setTreePositions(tree, defaultVertexSize, DpOffset(10.dp, 10.dp))
                        indicator.value = (indicator.value + 1) % 10
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                TreeButton("Find") {
                    tree.find(it)?.let { pos ->
                        scrollDelta.x = -pos.x + 335.dp
                        scrollDelta.y = -pos.y + 335.dp
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        setTreePositions(tree, defaultVertexSize, DpOffset(10.dp, 10.dp))
                        scrollDelta.x = 0.dp
                        scrollDelta.y = 0.dp
                    },
                    modifier = Modifier.width(260.dp).height(45.dp),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = "Reset Positions", style = defaultOnPrimaryLargeTextStyle)
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { TODO() },
                    modifier = Modifier.width(260.dp).height(45.dp),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = "Save Tree", style = defaultOnPrimaryLargeTextStyle)
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { TODO() },
                    modifier = Modifier.width(260.dp).height(45.dp),
                    shape = RoundedCornerShape(5.dp),
                ) {
                    Text(text = "Upload Tree", style = defaultOnPrimaryLargeTextStyle)
                }
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd)
                .fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}


@Composable
fun TreeButton(
    textButton: String,
    action: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    Row() {
        Button(
            onClick = {
                if (text != "") {
                    action(text)
                    text = ""
                }
            },
            modifier = Modifier.width(115.dp).height(45.dp),
            shape = RoundedCornerShape(5.dp),
        ) {
            Text(text = textButton, style = defaultOnPrimaryLargeTextStyle)
        }
        BasicTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.width(145.dp).height(45.dp)
                .border(
                    1.dp,
                    defaultVVBrush,
                    RoundedCornerShape(5.dp)
                )
                .padding(defaultPadding * 2),
            textStyle = defaultLargeTextStyle,
            singleLine = true
        )
    }
}