package io.github.cquiroz.scalajs
package react
package virtualized

import org.scalatest.{Assertion, FlatSpec, Matchers}
import Column._
import japgolly.scalajs.react.ReactDOMServer

import scala.scalajs.js
import js.JSConverters._
import japgolly.scalajs.react.vdom.html_<^.{< => <<, _}

class ColumnSpec extends FlatSpec with Matchers {
  def assertRender(e: japgolly.scalajs.react.vdom.VdomElement, expected: String): Assertion =
    assertRender(e.rawElement, expected)

  def assertRender(e: japgolly.scalajs.react.raw.ReactElement, expected: String): Assertion = {
    val rendered: String = ReactDOMServer.raw.renderToStaticMarkup(e)
    rendered should be(expected)
  }

  "Column" should
    "require width and dataKey" in {
      Column(Column.props(200, "key")).props.width should be (200)
      Column(Column.props(200, "key")).props.dataKey should be ("key")
    }
    it should "support ariaLabel" in {
      Column(Column.props(200, "key")).props.`aria-label` should be(())
      Column(Column.props(200, "key", ariaLabel = "Label")).props.`aria-label` should be("Label")
    }
    it should "have a default cellDataGetter" in {
      val dataMap = Map("key" -> 1, "b" -> 2).toJSDictionary
      Column(Column.props(200, "key")).props.cellDataGetter.toOption.map(_(CellDataParameter("col", "key", dataMap))) should contain(1)
    }
    it should "support cellDataGetter" in {
      Column(Column.props(200, "key", cellDataGetter = Some((x: CellDataParameter) => "abc"))).props.cellDataGetter.toOption.map(_(CellDataParameter("col", "key", "row"))) should contain("abc")
    }
    it should "have a default cellRenderer" in {
      Column(Column.props(200, "key")).props.cellRenderer.toOption.map(_(CellRendererParameter("cellData", "col", "key", "row", 1))) should contain("cellData")
    }
    it should "support cellRenderer" in {
      Column(Column.props(200, "key", cellRenderer = Some((x: CellRendererParameter) => "abc"))).props.cellRenderer.toOption.map(_(CellRendererParameter("cellData", "col", "key", "row", 1))) should contain("abc")
    }
    it should "support className" in {
      Column(Column.props(200, "key")).props.className should be(())
      Column(Column.props(200, "key", className = "my-class")).props.className should be("my-class")
    }
    it should "support columnData" in {
      Column(Column.props(200, "key")).props.columnData should be(())
      val dataObject = js.Dynamic.literal(foo = 42, bar = "foobar")
      Column(Column.props(200, "key", columnData = dataObject)).props.columnData should be(dataObject)
    }
    it should "support disableSort" in {
      Column(Column.props(200, "key")).props.disableSort should be(())
      Column(Column.props(200, "key", disableSort = true)).props.disableSort.toOption should be(Some(true))
    }
    it should "support flexGrow" in {
      Column(Column.props(200, "key")).props.flexGrow should be(0)
      Column(Column.props(200, "key", disableSort = true, flexGrow = 1)).props.flexGrow should be(1)
    }
    it should "support flexShrink" in {
      Column(Column.props(200, "key")).props.flexShrink should be(1)
      Column(Column.props(200, "key", disableSort = true, flexShrink = 0)).props.flexShrink should be(0)
    }
    it should "support headerClassName" in {
      Column(Column.props(200, "key")).props.headerClassName should be(())
      Column(Column.props(200, "key", headerClassName = "my-class")).props.headerClassName should be("my-class")
    }
    it should "have a default headerRenderer" in {
      val label = <<.div("Label")
      val headerParam = HeaderRendererParameter(js.undefined, "key", disableSort = true, label, "key", "ASC")
      val unmounted = Column(Column.props(200, "key")).props.headerRenderer.toOption.map(_(headerParam)).map(x => HtmlTagOf("div").apply(VdomNode(x)))
      assertRender(unmounted.get, """<div><span class="ReactVirtualized__Table__headerTruncatedText" title="[object Object]"><div>Label</div></span><svg class="ReactVirtualized__Table__sortableHeaderIcon ReactVirtualized__Table__sortableHeaderIcon--ASC" width="18" height="18" viewBox="0 0 24 24"><path d="M7 14l5-5 5 5z"></path><path d="M0 0h24v24H0z" fill="none"></path></svg></div>""")
    }
    it should "support headerRenderer" in {
      val label = <<.div("Label")
      val headerParam = HeaderRendererParameter(js.undefined, "key", disableSort = true, label, "key", "ASC")
      val unmounted = Column(Column.props(200, "key", headerRenderer = Some(x => <<.div("header").render.rawNode))).props.headerRenderer.toOption.map(_(headerParam)).map(x => HtmlTagOf("div").apply(VdomNode(x)))
      assertRender(unmounted.get, """<div><div>header</div></div>""")
    }
    it should "support id" in {
      Column(Column.props(200, "key")).props.id should be(())
      Column(Column.props(200, "key", id = "id")).props.id should be("id")
    }
    it should "support label" in {
      val label = <<.div("id").render.rawNode
      Column(Column.props(200, "key")).props.label should be(())
      Column(Column.props(200, "key", label = label)).props.label should be(label)
    }
    it should "support maxWidth" in {
      Column(Column.props(200, "key")).props.maxWidth should be(())
      Column(Column.props(200, "key", maxWidth = 100)).props.maxWidth should be(100)
    }
    it should "support minWidth" in {
      Column(Column.props(200, "key")).props.minWidth should be(())
      Column(Column.props(200, "key", minWidth = 100)).props.minWidth should be(100)
    }
    it should "support style" in {
      val style = js.Dynamic.literal(foo = 42, bar = "foobar")
//      Column(Column.props(200, "key")).props.style.isEmpty should be(js.Object.apply().toLocaleString())
      Column(Column.props(200, "key", style = style)).props.style should be(style)
    }
}
