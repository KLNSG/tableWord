package com.mark.springboot.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.mark.springboot.dao.QueryDao;
import com.mark.springboot.domain.TableFileds;
import com.mark.springboot.domain.Tables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * desc * 创建word文档 步骤: 1,建立文档 2,创建一个书写器 3,打开文档 4,向文档中写入数据 5,关闭文档
 *
 * @author ：Lxin
 * @date ：Created in 2020/10/12 15:27
 **/
@Service
public class DateToWordUtil {

    @Autowired
    QueryDao queryDao;


    public void toWord(List<Tables> tables, String fileName) {// 创建word文档,并设置纸张的大小
        Document document = new Document(PageSize.A4);
        try {
            // 创建word文档
            RtfWriter2.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Paragraph ph = new Paragraph();

            Font f = new Font();
            Paragraph p = new Paragraph("word", new Font(Font.NORMAL, 24, Font.BOLDITALIC, new Color(0, 0, 0)));

            p.setAlignment(1);

            document.add(p);

            ph.setFont(f);
            for (int i = 0; i < tables.size(); i++) {
                String table_name = tables.get(i).getName();
                String table_comment = tables.get(i).getComment();

                List<TableFileds> fileds = queryDao.getTable(tables.get(i).getName());

                String easy = Fanyi.easy(table_name.replaceAll("_", " "));

                System.out.println("正在执行："+easy);

                String all = "" + (i + 1) + "、" + easy + "表" + "（"+table_name+"）";//表头

                Table table = new Table(5);//6列

                document.add(new Paragraph(""));

                int width[] = {30,20,10,10,30};//设置每列宽度比例
                table.setWidths(width);
                table.setWidth(90);

                table.setBorderWidth(1);

// table.setBorderColor(Color.BLACK);

                table.setPadding(0);

                table.setSpacing(0);

                /*
                 * 添加表头的元素，并设置表头背景的颜色
                 */
                Color chade = new Color(255, 255, 255);

                Cell cell = new Cell("列名");// 单元格

                cell.setBackgroundColor(chade);

                table.addCell(cell);

                cell = new Cell("类型");// 单元格

                cell.setBackgroundColor(chade);

                table.addCell(cell);

                cell = new Cell("主键");// 单元格

                cell.setBackgroundColor(chade);

                table.addCell(cell);

                cell = new Cell("是否为空");// 单元格

                cell.setBackgroundColor(chade);

                table.addCell(cell);

                cell = new Cell("字段说明");// 单元格

                cell.setBackgroundColor(chade);

                table.addCell(cell);

                table.endHeaders();// 表头结束
                // 表格的主体
                for (int k = 0; k < fileds.size(); k++) {
                    String Field = "  "+fileds.get(k).getField();

                    String Type = "  "+fileds.get(k).getType();

                    String Key = fileds.get(k).getKey().equals("PRI")?"是":"否";

                    String Null = fileds.get(k).getNull().equals("YES")?"是":"否";

                    String Comment = fileds.get(k).getComment();

                    table.addCell(Field);

                    table.addCell(Type);

                    table.addCell(Key);

                    table.addCell(Null);

                    table.addCell(Fanyi.easy(Field.replaceAll("_"," ")));
                }
                Paragraph pheae = new Paragraph(all);
                //写入表说明
                document.add(pheae);
                //生成表格
                document.add(table);
            }
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
