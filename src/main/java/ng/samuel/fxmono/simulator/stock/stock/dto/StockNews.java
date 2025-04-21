package ng.samuel.fxmono.simulator.stock.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockNews {
   private String symbol;
   private String publishedDate;
   private String title;
   private String image;
   private String site;
   private String text;
   private String url;
}
