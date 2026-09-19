package com.yiyiaddon.e.c.i;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;

public final class i implements e {
   private static final int bt = 2;
   private static final int bu = 2;
   private final com.yiyiaddon.e.c.d.d b;
   private final com.yiyiaddon.e.c.c.h g;
   private final double n;
   private InteractionHand a;
   private boolean N;
   private int aX;
   private int aY;

   public i(com.yiyiaddon.e.c.d.d var1, com.yiyiaddon.e.c.c.h var2, double var3) {
      this.b = var1;
      this.g = var2;
      this.n = var3;
   }

   public com.yiyiaddon.e.c.d.d c() {
      return this.b;
   }

   @Override
   public l a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3t6wki4aav15e","Yv5Bh+pdllQZjVrsHPElxUlyVM+RetL8bivt2E2pUvk=",5542999575976842642,3803256032481511789,5855445987540263217,-7840225643777419175>()) {
            case 1511156812:
               return l.TARGET_INVALID;
            default:
               throw null;
         }
      } else if (!this.b.a().a(var1.level, this.b.a())) {
         switch ((int)com.yiyiaddon.m.b.a<"s25hqk6h7r1ps6","Ece4D7DWDg2ffeCCLkoZRjK8R0C5ZMasAE7TuBT+exY=",731233628159418727,4050348776343322433,3048559531676344867,9110774249647733408>()) {
            case -1563778235:
               return l.TARGET_INVALID;
            default:
               throw null;
         }
      } else if (!this.R()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dwjik0j9jep","wo7R6aQ+RDyWG6q7H549+E+f4i5ZwW0O2OZMrDmEZ+Q=",-8167636319946910978,-6747288113776059233,2385477655362527032,-2799996362504947855>()) {
            case 497364688:
               if (!com.yiyiaddon.i.c.a.ft()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jjwm9nurnvv4","pv0GO7dKGgMhCar5YPNdtuSP3oCMXIBqIPoEQiq361k=",6860885734851347561,-3307839642666346467,-771390634851339023,8375620662136217106>()) {
                     case 854434391:
                        return l.NAVIGATION_FAILED;
                     default:
                        throw null;
                  }
               } else {
                  if (!com.yiyiaddon.i.c.a.cX()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s26tygfnbcv0uy","oBff4e7SzsmRBjagxtYjoocOub3MsnaLDlx5KeJMRGc=",-4546416508442023075,-6352494806258172642,2383078375892287975,4906530143861035254>()) {
                        case 1891612253:
                           com.yiyiaddon.i.c.a.b(this.b.a(), 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s2t0l358s4woz8","WZdfw/j6RsuSqkawzRYmv63VpzIRu4qbC5UW9erFSP0=",-1760341356686366236,4873336252242717140,1682585621913044593,-3566790999916469237>()) {
                              case 1020541627:
                                 return l.IN_PROGRESS;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return l.IN_PROGRESS;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.i.c.a.i();
         if (this.a == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sbcve5x5wj32b","/2gLYjrc3nTMld9SeRuH5ePyRNjR0lRW8F9xCFMRTFY=",5410821280073294005,9160241784800357844,-1940633335608339716,-2348733641016669165>()) {
               case 710948671:
                  this.a = this.a(this.b.a().a());
                  if (this.a == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sgrw6wpg1f33t","/7QmWt4qQOoamV6ht00UN45Cif8Q3bYo5BykStDVz8A=",7284805311711123241,7147205785931444848,-8785612509138531715,509567936559941925>()) {
                        case -206124359:
                           return l.RESOURCE_INSUFFICIENT;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (!this.N) {
            switch ((int)com.yiyiaddon.m.b.a<"s1717girwgdncp","SZ46f69DKwhGH4TOQOEICAFIXsXxJx7UC9IqmDQgW7c=",4126004042798914424,3174599732646042780,4310206267685347340,5385334347609531246>()) {
               case -242873017:
                  com.yiyiaddon.i.d.a.a(this.a, this.b.a());
                  this.N = true;
                  this.aX = 0;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else if (this.aX < 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3gicqyq0xbf95","OSKmsn82nlF0gGbLFiOIc3rxp8b1ib9QdXaFkOjQSTs=",-4878991050472955301,4644480377491132908,-8937763229870199918,-4175077144740634804>()) {
               case 1244460304:
                  this.aX++;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else if (this.g.b(this.b)) {
            switch ((int)com.yiyiaddon.m.b.a<"sv7w88v6i43lo","Yiz3wfOi1II2yYs6p0z5ha/t7WCWYVwCwxK5AspGh6Q=",-202268271015514922,-485837348034931472,-1679277614993017656,6283623580530112680>()) {
               case -707793580:
                  return l.SUCCESS;
               default:
                  throw null;
            }
         } else if (this.aY < 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s1wa8uk5lx1n8h","UZcGe/UHM6IdwIcWdPMBav/F+CnxdBuE/L+/IKjmeng=",7814478277260906241,-2104539690699623916,862510869669688348,-3674580902425753782>()) {
               case 1604894688:
                  this.aY++;
                  this.N = false;
                  this.aX = 0;
                  return l.IN_PROGRESS;
               default:
                  throw null;
            }
         } else {
            return l.PLANT_FAILED;
         }
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
   }

   private InteractionHand a(Item var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s278rrus77go6p","9xI7biw61lk/tnV6BikYS1jHjIXN/MylGfFcsbhKaaA=",4289351819921775164,-906685234234977520,282978607771012337,4742755106350887010>()) {
            case 1939369928:
               return null;
            default:
               throw null;
         }
      } else if (var2.player.getOffhandItem().is(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s42pclcy22e33","Sovr5B06k6vg6SMgt+xyTUQ+r/dubsFVRRiifjiFCPQ=",-3912167349678538390,3045327644210460101,-2800718768109784695,9025007040942520398>()) {
            case -1770980186:
               return InteractionHand.OFF_HAND;
            default:
               throw null;
         }
      } else {
         int var3 = h.a(var1x -> var1x.is(var1));
         if (var3 != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1pcw5tzv5rxs0","xY8acFusEziPri3g0zZ/livmWBj8n0baHXqBDKQ7lPY=",7293370361242892785,-4963019860151942396,-2532939591012596301,1907457475567953088>()) {
               case 707389028:
                  h.c(var3);
                  return InteractionHand.OFF_HAND;
               default:
                  throw null;
            }
         } else {
            int var4 = h.b(var1x -> var1x.is(var1));
            if (var4 != -1) {
               switch ((int)com.yiyiaddon.m.b.a<"s34o3cakeo6iv","tJgfrFtN5nJtLzli11ftLtl5RPATt10e8f3oMEYxmn8=",4097898141248333600,1264559912509312550,-7512600351341672997,2959534845654561760>()) {
                  case -305159249:
                     h.c(var4);
                     return InteractionHand.OFF_HAND;
                  default:
                     throw null;
               }
            } else {
               return null;
            }
         }
      }
   }

   private boolean R() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uf5uiq6oy7qf","LLTDFsYFnukEZsKG3asc1CCnzFqhZ20NjWpwjQqTtFI=",4909170713680642967,7752863712017567462,3375671797610033402,1435450515235930349>()) {
            case 1807920801:
               return false;
            default:
               throw null;
         }
      } else {
         double var2 = this.b.a().getX() + 0.5 - var1.player.getX();
         double var4 = this.b.a().getY() + 0.5 - var1.player.getEyeY();
         double var6 = this.b.a().getZ() + 0.5 - var1.player.getZ();
         if (var2 * var2 + var4 * var4 + var6 * var6 <= this.n * this.n) {
            switch ((int)com.yiyiaddon.m.b.a<"s1vr1slg0z9lu1","PMEJWyTYvuL1dWyF0RoaS2FUgp/KGrqABg7FuUDsEEo=",-4806896771839808992,5462164463038209298,3379063521812973274,-1978096963469570230>()) {
               case 766525334:
                  switch ((int)com.yiyiaddon.m.b.a<"soxfklg70fyqf","JspNHJZBuO6IlF1wNmP2KSXuWlzNDc4VoGr7VeNdttA=",-3283812003669513546,-8113850194135263870,2608667408285791710,-5861582562689229780>()) {
                     case 1998001615:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s37ynly6c1ukc2","z6E+I7DqxAiQo/TpfCpqtD0oY881wrKcqP8nndWBOqo=",-7165817747925178626,-3528196134190424867,-4319320516746277698,-5559336936132584547>()) {
               case 234541839:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }
}
