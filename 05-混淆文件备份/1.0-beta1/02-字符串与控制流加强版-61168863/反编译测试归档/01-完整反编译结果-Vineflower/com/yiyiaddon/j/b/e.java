package com.yiyiaddon.j.b;

import com.google.gson.JsonObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class e {
   private final Set<String> aJ = new LinkedHashSet<>();

   public Path k() {
      return com.yiyiaddon.i.f.a.i();
   }

   public String a(com.yiyiaddon.g.c.d var1, boolean var2) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s119qh8hvci5r8","EIalJouxWBhRE2KmKOdb6fx4K9S0egmYh9pZbc4AC9s=",7085764751392905048,-7721063152845625530,-8108796761861206913,-1257396895231409343>()) {
            case 293178067:
               return null;
            default:
               throw null;
         }
      } else {
         String var3 = var1.fI();
         if (!var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s1b5ho4kcogiys","HKlNDTyrkEc6UZ+4l159cbO9kg0SXHr3Ad9ysLoHnYk=",-7148077288624328307,-5542912934438491024,7196142985653350638,915631320463167712>()) {
               case -210614952:
                  if (this.aJ.contains(var3)) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssdozhf5oq2zb","BY0USZAekusmsBw7TZDrxE8TLxVzmyxRl56hB5dp5Ck=",-3566271531206526511,-3031524823514859643,-4833129086419436696,-5412764340688767915>()) {
                        case -2127667608:
                           return null;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         String var4 = com.yiyiaddon.m.a.B(var1.m(), var1.be());
         String var10000;
         if (var2) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"s88al4w8tusxe","Ib+45d+xNkAIJeywMdawTQ+JmqKNQhGO/NTHGsysjQk=",-8596141850221853004,-5392545812353468740,-179326284998839776,152322203340357557>()) {
               case 1301483699:
                  var10000 = var4 + var3 + System.currentTimeMillis();
                  switch ((int)com.yiyiaddon.m.b.a<"s3gdsgqazx2s84","xJz4DU3X/lJICoWGUG04rjZqDZ4Fw0mtIzfOD8GcIsE=",-1475838234519383641,-8408677554295475155,7291990397268402021,3574949843222021547>()) {
                     case -899330187:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var4 + var3;
            switch ((int)com.yiyiaddon.m.b.a<"s24iu6bwwsdg1k","gfBEqPODaol5z3ZNkDd73nrK2UXEbpooWV6ZHepfWk8=",8754039673478744281,-7169053095904704018,160534137173712302,-4339202745860910175>()) {
               case 1178712868:
                  break;
               default:
                  throw null;
            }
         }

         String var5 = var10000;
         Path var6 = com.yiyiaddon.j.a.a(this.k(), var5);
         JsonObject var7 = var1.f();
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s1s6bql2byx7pb","aI3Ku3zpmPjEnujBsuu0JnL1SlYkXMBFgjqxDQSw/ljR/2wa",-2963201796993383167,3048999071776763996,-8408380832948100574,-3433081598408534320>(),
            var3
         );
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s11teus4cdbaho","BTdJg5+ao155Fz/AoINZvvrQ73gWUkjixgAE0m7adNcFcqly",-8989077266103416731,5140455858323547364,4300938489172049095,-5292769559261850265>(),
            System.currentTimeMillis()
         );
         if (!com.yiyiaddon.j.a.a(var6, var7)) {
            switch ((int)com.yiyiaddon.m.b.a<"sr7p2itkppa9b","7VeX3pGp2xGtN+dKQzxQgiuPTqUgel4izqVig8t4bS8=",-4400828581155771424,-8567925152741304775,-3936327314788049485,531103541294547163>()) {
               case 404062513:
                  return null;
               default:
                  throw null;
            }
         } else {
            this.aJ.add(var3);
            return var6.getFileName().toString();
         }
      }
   }

   public void C() {
      this.aJ.clear();
      Path var1 = this.k();
      if (!Files.isDirectory(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q6h6mo45y83m","7x48Rn1mx4A2Mcx6v+H/GYTvxZsP4omyxoiTUSsEZx4=",1496403395104337570,-5244038176033134435,8240444224095159881,9057257341028596407>()) {
            case 111101262:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var2 = com.yiyiaddon.j.a.b(var1).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2la3yxzzqj623","B/hsxtwqGUyLD4WHKiXNxbL9uR1lbL8PYtXCC/IM4DI=",4180690941646947074,577997600472924116,7955610758307053698,5152533011718196266>()) {
            case -1881424429:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3536zuf4pnae2","cbGvOqEFyYwnLlSXPfCt+fhwYYIDZXEHHB5qcETXXCk=",-7102762196985695504,-6470619514712346375,7051438781594923077,7078216527619982751>()) {
                     case 767497269:
                        JsonObject var3 = (JsonObject)var2.next();
                        if (var3.has(
                           (String)com.yiyiaddon.m.b.a<"s1s6bql2byx7pb","aI3Ku3zpmPjEnujBsuu0JnL1SlYkXMBFgjqxDQSw/ljR/2wa",-2963201796993383167,3048999071776763996,-8408380832948100574,-3433081598408534320>()
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2gq7fartl2jww","O08IsFg7BZeU1R9dGnji03iDTTiGHKfLgB0aR5pHBH0=",-1059770768063025627,403632582111833318,3123312066075140825,9153158115065622775>()) {
                              case -1434987031:
                                 if (!var3.get(
                                       (String)com.yiyiaddon.m.b.a<"s1s6bql2byx7pb","aI3Ku3zpmPjEnujBsuu0JnL1SlYkXMBFgjqxDQSw/ljR/2wa",-2963201796993383167,3048999071776763996,-8408380832948100574,-3433081598408534320>()
                                    )
                                    .isJsonNull()) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"s93ua2v4v88m3","YLQMXliyeIxE/8pqlwYCsgYMoygp3UcOyFzilLfU1O8=",-810469882072163794,-6242351358553630072,2217699934647198025,7968296022484939538>()) {
                                       case -1705309667:
                                          this.aJ
                                             .add(
                                                var3.get(
                                                      (String)com.yiyiaddon.m.b.a<"s1s6bql2byx7pb","aI3Ku3zpmPjEnujBsuu0JnL1SlYkXMBFgjqxDQSw/ljR/2wa",-2963201796993383167,3048999071776763996,-8408380832948100574,-3433081598408534320>()
                                                   )
                                                   .getAsString()
                                             );
                                          switch ((int)com.yiyiaddon.m.b.a<"s3ihucdd8fuar9","2OpHpdLiv3uv+pg5+qghs+8oa767I7GziOQQvv4JSNo=",2671255454137568523,-7209964510110550670,-6763313156659373486,-6516605654015464294>()) {
                                             case -2069866276:
                                                break label33;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"shoglsqqhid1","55xYbRDN56DhmrELQNsIb8gVCrV98Qtx1IITL8bg92g=",2605859430237709020,2023074796166384027,8072783900768809625,3219435334283863554>()) {
                           case -1372231046:
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
   }

   public boolean fx() {
      boolean var1 = com.yiyiaddon.j.a.b(this.k());
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jtk7qx7a20tb","QxJXKU2bhkBS00wZpE/LWfXcNxc89L0EigyxafW0x6A=",4178524509096944216,-3871105792034826902,8165858367435125317,1392199683028808250>()) {
            case -254015650:
               this.aJ.clear();
               switch ((int)com.yiyiaddon.m.b.a<"s3b948ws68xubx","A/jk1bxvLS1YkL4qMNpo0wKmD50c0BaUTswC/z8u0Oo=",-6155806715140098718,6644714175386500167,-8881589244646743441,-2335642697084226287>()) {
                  case 588777860:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1kjwb7xmd2eoz","kq+KkHffx0lFy4k37KH+s40rV6iFmgBUuq+PD27oY2w=",5071509296212370530,-6237777581660320837,712991548368411493,-2899434082719293597>()) {
            case 1900756544:
               return var1;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.aJ.size();
   }

   public boolean dt() {
      if (!this.aJ.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"syvxf6wd7wklg","gjwuDo/4ZUi85VZqPjs2zgQdXgE0Y/DBj+akQAkQFyM=",-1803245798543228726,6202281368203538281,3993165556016368774,6970597258528428835>()) {
            case -2031101808:
               switch ((int)com.yiyiaddon.m.b.a<"s1qpstm2qyyl09","J/NrG6M/8Njg7Mk/Qr07NWpug1pv+q8quKpmYFvHLUQ=",-2831458973863226554,4179014535517531728,5159572350221913427,-715476248460944516>()) {
                  case 511495140:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3tcrjkyizcn6n","OWIyGg2I+zQkrc6wuU1JpZ0V8M9IONLzJoJ20Vx7cT4=",2349660076223244643,8789253302436640755,-3710963279918997428,3844830289900868546>()) {
            case -744732959:
               return false;
            default:
               throw null;
         }
      }
   }
}
