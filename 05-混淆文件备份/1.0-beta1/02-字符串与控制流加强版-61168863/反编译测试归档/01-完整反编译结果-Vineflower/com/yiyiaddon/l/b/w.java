package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.function.Supplier;

public class w implements g {
   public static final float hK = 22.0F;
   private static final float hL = 11.0F;
   private static final float hM = 6.0F;
   private static final float hN = 0.36F;
   private final Supplier<String> o;
   private float hO = 22.0F;
   private float hP = 11.0F;
   private boolean fM;
   private float hQ;
   private int to;

   public w(String var1) {
      this(() -> var1);
   }

   public w(Supplier<String> var1) {
      this.o = var1 == null
         ? () -> (String)com.yiyiaddon.m.b.a<"s2073l8c1k8aev","DDuKIAOu4xqt7qcL0VpI4OYd9In6B5hCwjUy8Q==",-8147384290045785139,5709770582242625104,7147892802544578503,3353029145266887066>()
         : var1;
   }

   public static w a(String var0, String var1) {
      return new w(
         var0
            + (
               var1 == null
                  ? (String)com.yiyiaddon.m.b.a<"s20qv6a4r0ounn","b1C1GptC1DK+7dlifKBmndBz7cF/wA2OJnum3//2",6865209455766744900,-7793063824151270639,-7759997323853925257,-3013865130334657760>()
                  : var1
            )
      );
   }

   public w a(float var1) {
      if (var1 > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s199xfhy2p487p","kqmuEPf9iBlSVoNvDxEqCSpO0uO1kMRBg0aDQ93uoTM=",-3050367220316343425,7415177968867244186,1946258098999670055,-9180990328407429968>()) {
            case -473424836:
               this.hO = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s25njup199yuat","VwHTQQ6iO6edCDrk6Ldqqmqiout5wOW/GvPgebHORE0=",218423838660793549,-3305477056167314584,5187556493227266188,-7495045934538103310>()) {
                  case -1657960786:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   public w b(float var1) {
      if (var1 > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s397plkl3nv4fc","Y3kx2JbDsvpVpvhNhLStVKXMUsMZrkUlXuBbVG7Lzlc=",-4095423713696220717,4338571341353929660,6448890060161372857,-2709906342173689902>()) {
            case 427447177:
               this.hP = var1;
               switch ((int)com.yiyiaddon.m.b.a<"snn9u1lnv78gp","2AkcL3Qx2inpJkzE+Qv0cSGpeLLIelZMw5jTFI3wdYQ=",-2103857878851967741,4372963457284931587,9014229050452727477,-5533388557055112497>()) {
                  case -738149973:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   public w a(boolean var1) {
      this.fM = var1;
      return this;
   }

   public w c(float var1) {
      this.hQ = Math.max(0.0F, var1);
      return this;
   }

   public w a(int var1) {
      this.to = var1 & 16777215;
      return this;
   }

   @Override
   public float b() {
      return this.hO;
   }

   @Override
   public void a(float var1) {
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      String var8 = this.o.get();
      if (var8 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m882k31oytsp","XCMMcUyhj4/L/mYNuSC+nDIsvN4KeyZyrQRu+nHZWds=",-8529143424550411181,-2744044263927559589,-8868902705676929743,-962471577714141771>()) {
            case 125563410:
               if (!var8.isEmpty()) {
                  int var10000;
                  if (this.to == 0) {
                     label22:
                     switch ((int)com.yiyiaddon.m.b.a<"s2kes9sdt97k0w","FU3575N/xeue7xCRfNSBZS+8/IPnImJovPJBCwblBP8=",-3837167488220108758,2072207313530809961,-5907075578540975339,5963585358584421819>()) {
                        case -82559688:
                           var10000 = com.yiyiaddon.l.i.c.a().uT;
                           switch ((int)com.yiyiaddon.m.b.a<"sxbw27mzds4sv","E5J39QqEHDKYHa9GHchMu5VJt7Sourgi3e9tIGPkbFA=",573326022522084296,5207159070779952066,-3268057124192248283,4616988773697154078>()) {
                              case -56978489:
                                 break label22;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = this.to;
                     switch ((int)com.yiyiaddon.m.b.a<"s20de7mofxwq1l","oGWSnOwt9bPAD7vtSwlK/rWD3txJDzfnvx76EVVC+jA=",5799701028865921112,6746542422311469283,-1593167177254612900,-5767125365223386576>()) {
                        case 1226995926:
                           break;
                        default:
                           throw null;
                     }
                  }

                  int var9 = var10000;
                  com.yiyiaddon.l.g.d.a(var1, var8, var2 + 6.0F + this.hQ, var3 + this.hO / 2.0F + this.hP * 0.36F, this.hP, var9, var5, this.fM);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s13g4s7ntgw6i2","geoc0B20YLMvKn8eYfM7+/J9rxpr5LlIOIASk3wHNZc=",-8150018650297704199,-3478370199952955066,109085052135425731,-5790098688806571124>()) {
                     case -1898302380:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      return false;
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return false;
   }
}
