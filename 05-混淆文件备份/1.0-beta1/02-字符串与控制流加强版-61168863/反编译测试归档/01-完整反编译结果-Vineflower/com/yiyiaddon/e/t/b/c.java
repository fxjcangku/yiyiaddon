package com.yiyiaddon.e.t.b;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.h.d;
import net.minecraft.client.Minecraft;

public final class c extends com.yiyiaddon.l.f.c implements i {
   private static final String BH = (String)com.yiyiaddon.m.b.a<"s1ioiha1kngh6l","aWqlAh9sQ5zoqTq4cQW1BvLRbipJJMIFiBU1Jm4GEJ25wxh06fTLiWUq",-565403440424419942,2188616278899674141,1319501394169836304,-7250776350901908038>();
   private static final String BI = (String)com.yiyiaddon.m.b.a<"s8n7ksr3sfeqc","PyZIrfikRatkW+CldY1a2hMnF4PcdYH8mgfUKmBaVhoxU2zkFU8xwR7rLUTTYTxiTxFV28W9k904aYPry2qrmRRf5Sm3ettA08gdAgHYIAjwsD4Mlc9ay0Vh7JsUJdzlC0JNlA==",-1012830936219524115,-7269070295905412383,2988927445528188952,-1208960546730803147>();
   private static final String[] ak = d.a(d.a(com.yiyiaddon.e.t.b.b.r));
   private final com.yiyiaddon.e.t.a b;
   private boolean v;

   public c(com.yiyiaddon.e.t.a var1) {
      this.b = var1;
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      if (!this.v) {
         switch ((int)com.yiyiaddon.m.b.a<"sq214hocibuip","IpiUlelnx9qETx+JnmvJ9lTDL2pzP5gRAfUigMcBAjw=",-8410556741752990410,-5604964483707439025,3405375628095423650,8062362815119981244>()) {
            case -794955803:
               this.v = true;
               this.G();
               switch ((int)com.yiyiaddon.m.b.a<"s1kfgyj8ytlxpw","dKHwSOQU/mP5R/mEx76SME9lKHdLyKz/L60WYTWGlXg=",-1414655398418262122,2493390940183047175,-141670478989310638,1036282695345177360>()) {
                  case 941012640:
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

   @Override
   public String E() {
      return this.b.t();
   }

   @Override
   public String F() {
      return this.b.v();
   }

   private void G() {
      this.b(
         new h(
               (String)com.yiyiaddon.m.b.a<"s20vy37trl4hc2","y5p6joz2D9/xMrxPr1jEQDyucLREy6qEzfKK2g==",8611483775162859627,-3831183789321579285,3987064747011333225,-207633212574494250>(),
               () -> (String)com.yiyiaddon.m.b.a<"s8n7ksr3sfeqc","PyZIrfikRatkW+CldY1a2hMnF4PcdYH8mgfUKmBaVhoxU2zkFU8xwR7rLUTTYTxiTxFV28W9k904aYPry2qrmRRf5Sm3ettA08gdAgHYIAjwsD4Mlc9ay0Vh7JsUJdzlC0JNlA==",-1012830936219524115,-7269070295905412383,2988927445528188952,-1208960546730803147>(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s1ioiha1kngh6l","aWqlAh9sQ5zoqTq4cQW1BvLRbipJJMIFiBU1Jm4GEJ25wxh06fTLiWUq",-565403440424419942,2188616278899674141,1319501394169836304,-7250776350901908038>(),
                  this::H
               )
            )
            .a()
      );
      String[] var1 = ak;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sffn25tjpmcfd","8FllzqynVwtwGZAf4yKOiBHiHcUhnw1mes6W187kf+Q=",-5032207179987569741,7900805309035167496,-4836157200706197780,8246747015039781661>()) {
         case 1209153127:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s468378i6c9ct","wHLfxjg8tjIUfOVLmWLytJAyqItq9jIGRFNl2aUta/g=",-1669250191275201353,1486324021164329800,6019821491419394818,-3325087396911814729>()) {
                  case -808796377:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3qxxm5zteubtw","aOkAHDft224p7AT/pKci9GkkXINu6/9SWSyDi7JeHJQ=",-7417629692304052633,8850071058923777674,-2769699867603849907,-1269827121739853780>()) {
                        case -1926343368:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private void H() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sy7liyjgjikit","9SwEFdN4zd9LpIk2TVJuObI2RMoPiNuDP7RiM943o8w=",-4511186503982634751,-7886084916122280829,-5950171091340369400,5702378166568116382>()) {
            case 532595200:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new a(var1.screen, this.b));
      }
   }
}
