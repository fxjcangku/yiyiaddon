package com.yiyiaddon.g.c;

import com.google.gson.JsonObject;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Objects;

public record a(
   String CS,
   String CT,
   String CU,
   String CV,
   String CW,
   String CX,
   String CY,
   int sj,
   int sk,
   int sl,
   int sm,
   String CZ,
   String Da,
   String Db,
   String Dc,
   String Dd,
   String De
) {
   public JsonObject f() {
      JsonObject var1 = new JsonObject();
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1ewsivm3z73gm","v0tQJEaM/LG6liv2M+A+Ksqk3reqYQdd+JbK71EjR5394jw7",7588405504303476444,3240377505906177262,3357643426286769889,-3076661078591416207>(),
         this.CS
      );
      if (this.CT != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s361ldwhx8s13c","FORwkg8r9lJ4IvCnRNYV7yzI+MpbnfGltquB6k/Um+k=",-4320795481497437540,-4564602548127728296,9039686299020772274,6673893096078954037>()) {
            case 846569449:
               if (!this.CT.isBlank()) {
                  label106:
                  switch ((int)com.yiyiaddon.m.b.a<"s25jc8rpsvgwr4","E+c1HfViyyPBPnDAJOSaCBcPTTm2HU05NnpT9wIWwfI=",-8292812914731011871,-3259748031837755024,1437895492210076750,-5076567199517481462>()) {
                     case 850187608:
                        var1.addProperty(
                           (String)com.yiyiaddon.m.b.a<"s3l04e95ylmerv","N/kMDOk2+rB/twXJz0rI2A3Y3os0hnpsm9fnB36/ZZKnRgWP",-7309509075827059313,-7637568415042862277,-6812434940811375319,5599336517320795944>(),
                           this.CT
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s32he9ouffroe","M5OuRX1lYIFLIItUfB6AhiuCRCH6bk4JtGiLvUWnQPs=",-7212071495339495736,3428033543918157164,-3695918410439184945,4069376324080662764>()) {
                           case -23376639:
                              break label106;
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

      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s10zdvfhh3wing","xg6TRpmiB+EqpZrMNSTXqOi7QTDvaqVNOL/+9eRSJKSd9005",-472897258558336926,971639113233958926,-7072372602346453350,139891818208414928>(),
         this.CU
      );
      if (this.CV != null) {
         label101:
         switch ((int)com.yiyiaddon.m.b.a<"s3r4hk6qyq97fy","gVr1EWYtJz86X+Gulao3+jQisSzH7KcH67GCW/Dg940=",-6830572907720358449,990598927059877877,-1226199672263030116,6891826845832354277>()) {
            case 1036315618:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s16vyh4h2ofy1j","U4HhWiG//hsEK2cGMZEu2AlYU0xiq7aDZmrTKAyu5gdpYQmr9YH53A==",-8710943376299177299,3135662738945492468,-808515802329427118,1476540835577200170>(),
                  this.CV
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1ubwusfs2oxwm","bAB6ekAfDPZGqbqesuR+FPL7K993W0xWG6CrjiWzsnE=",-375702517622988660,-170252896457077183,-5864065084099920921,4079523635554947279>()) {
                  case -1600228751:
                     break label101;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.CW != null) {
         label96:
         switch ((int)com.yiyiaddon.m.b.a<"s2pb9w5r59eg9r","9PTdteS9ryIGuFMeSsRvkZm/sqkIPdXd5go5Mizf75Q=",-880414207524449547,-8541418995581991690,1457884261763857894,6785908748828750359>()) {
            case -117516634:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s1cfj347aeucki","4p6VOr19x/qkAvduubQPg7oL/kPf6okiape49etdpdyFz/UWOMAxbg==",4874868067709816375,-8678998961097363296,-7961377688576894308,4442585696485701238>(),
                  this.CW
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3srdv2h5v8j94","mbiM/VMUGJNWwvHkqfiZ4HV1j3m/7kVVhr307uNm55I=",1277190387334105612,707982446968361296,-152855090657827970,-8869928251243816604>()) {
                  case 1097785169:
                     break label96;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"stvvclfld7thl","fvEVUZtcAR/Ja41qq70IJJFeB4NKieztElZ5HGjQttQ=",3456038509090716726,855797774566183158,8838120144291849498,6052227476762026509>(),
         this.CX
      );
      if (this.CY != null) {
         label91:
         switch ((int)com.yiyiaddon.m.b.a<"s2kxzikzjuevfp","KEt2Y2oyZqIXO8FdDWFECWPMUrg/efrlkpqTtPLs5XY=",-2147544731222754438,3889182698944369520,-1251342486831399512,-319277233481368105>()) {
            case 475800315:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s3ckcfxfnkucs1","B7UxxVa9Rft5DIUbY8OTsa4Mc7I5PT6vnRzcY/jE3P0vmg==",4452762978239886055,2217292611826827908,-7100994274139826312,9097477612787050250>(),
                  this.CY
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2ly8wfb9fdehw","vEYajd7OBaeSQfT90izmqfxRVXmWFF3eO96Vfll/y+k=",-318750413308622254,-3460414764073748481,7380714401354385563,3710414771583261472>()) {
                  case -1574989536:
                     break label91;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3oc2evmcfm0xd","Wa+8fXSkT0MZvWaBAy+LIPTW+CpXauO+Iuc8jMG58slhHg==",1298395397186620694,8967166744629499451,-1337118261567091630,5619662554445884543>(),
         this.sj
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sw7mkuleiowp2","t1uNstYX1/S8lJJZRTsIg9M1MtLnoOWr2anEjc//apBR+Q==",-7749999580384049546,-6381783771435241200,7686458312342448673,-1543880157256586491>(),
         this.sk
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1jfrbumlsl6uv","t7ukLetPvSFQhq6WLWm1hF7PhwJ4ygXspohU4epL+sLTAw==",8856793147327121935,-2358360787963372536,1898941977399784044,4219422678519681929>(),
         this.sl
      );
      var1.addProperty(
         (String)com.yiyiaddon.m.b.a<"sije8qjvgp8ij","W88cKuzo8WLS4NlzANrN1TxY9cb9raTORWYs0iGzLqp6LOm9",937053695536680546,-7279203208357157492,-4447042663754921106,-1620803921878569359>(),
         this.sm
      );
      if (this.CZ != null) {
         label86:
         switch ((int)com.yiyiaddon.m.b.a<"s2ytcp07lwe5lu","fN5qJAi1spmd/nmbZhyHNmK+ohc+9WH7kyEWNsw8B3I=",-4942194417305430117,3085818245167963850,8852378840695585701,5015386467459051262>()) {
            case 531643416:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s125jqz55amvey","EGnjs3bPt7nteK2iv5OkJP6kLoGNi0y9ychsbrx5gm+N9XAD",-269235457848065931,-7963275467796622202,-5155377729906010444,-3698954969397256572>(),
                  this.CZ
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3dvxkqluwtiyk","qnLWx8C19Kwj/PwZZUe510pMKY0al3BXiZzeA57fZz4=",-2314621217536760020,6504207102466146864,4849549743150283271,413170603539937759>()) {
                  case 1372865050:
                     break label86;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.Da != null) {
         label81:
         switch ((int)com.yiyiaddon.m.b.a<"s3ihi1aarvu3j9","odkYZgSUY5p3VR83eplxjsgYOy9Xj0IEj83dgfJtrGo=",-4136538965144491205,58621057285360427,8386620793670954947,-2914402611444480691>()) {
            case -1182895218:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"sa1zrte132td1","oEnXq6IDyoO73jOoKE1tTS/iByloExw5h3e72JNgJ3Lw6vriOAs=",8693147795922589145,5575198743349715775,-512490096539862791,6309494777404712098>(),
                  this.Da
               );
               switch ((int)com.yiyiaddon.m.b.a<"s63m4dqy40if4","lvd2JRI/tO2aKgu5iNym5q2Zp6poJqnLi6f80R768gM=",-313462662629480325,8208339274252417058,-2733635408374864414,-3432636000236790767>()) {
                  case 1207175717:
                     break label81;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.Db != null) {
         label76:
         switch ((int)com.yiyiaddon.m.b.a<"s2k1h2w0kswui","be6igCvjM/2ZfpBs71qGw5c9n45MeHm3iRLA4Ds9cYg=",-3076173638347441843,8306449212959449417,-5817137583228699834,2102770815522486522>()) {
            case 1755073794:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s14i2ex8qqqk0p","KUxiKg54agjcFHH2eflv9rz49BcXNPo2M6h7PXYe/ynXqWppGCg=",-6215571872924679172,-8618462353508776914,1797349972195735527,-2245873847835875109>(),
                  this.Db
               );
               switch ((int)com.yiyiaddon.m.b.a<"s343hotkfgp20g","Y0E8T4KBgaHYZAIkXVo+aJJdLiZfQtLjupoAn5d1xaY=",-6544839086087237269,-9141327807560433760,5252371339121915859,8391437850994244887>()) {
                  case 1835882577:
                     break label76;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.Dc != null) {
         label71:
         switch ((int)com.yiyiaddon.m.b.a<"s3mea6239rnfz9","zCvjwp0k3UDilUaIDMd4bcF2QMejAmlYCMRBMQFIjuU=",2844533768829802628,-6326198950428466262,42301502793024548,5769389680826513756>()) {
            case 1196618436:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s1gy175fbv0mj6","NVO8x6iC0JdSfA0GHjT40UeIYIR0402jFbAHXZIx5VVM4VjD",-2790835868676846639,-4505569228878697280,-4729108105078438195,-6784771173949784545>(),
                  this.Dc
               );
               switch ((int)com.yiyiaddon.m.b.a<"st3rd4q4w2pvy","R4C9y8HW5TVH2LqxXZC+dOfalVAa6MxW0gKn/LNNcks=",115306146823207173,-2017456650237227536,-6520129217685493293,4380813968450559274>()) {
                  case -1519889795:
                     break label71;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.Dd != null) {
         label66:
         switch ((int)com.yiyiaddon.m.b.a<"s2o4633gaqq1wq","Lb5RrHEF82RnJuXNiXBt9EwphpfR3Y+W0/Pu4wTfjlg=",-8818186898091815634,-2030107915605843870,2994985536363374796,-1744100125165988720>()) {
            case -1070835992:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"slbnaqp4x87lk","g4EHyum3soQI0eq4g69JD+thcCURVvSHI7Tk1I7+1dEHel3P",-1998305288996134908,4734738090396638002,-9053178051278775358,6558035527969482349>(),
                  this.Dd
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3kooktvon427e","Kmw5/hqoMbi49azbaXNwhjaJk8jb93moWdVmAZp8Da0=",-2368814909214382849,-6348699655466661755,-6805945672855056992,-1679093448812754531>()) {
                  case 483270325:
                     break label66;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.De != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12xzed7h0fpz4","a24sVWGy4TwKlUpJxKAIlLdNehxK/umiyf/PEt9f8Wg=",-909118099414618843,-8646365200647697056,1611049784500082497,3092838798157271542>()) {
            case 111104147:
               var1.addProperty(
                  (String)com.yiyiaddon.m.b.a<"s2534pv0ikq0lt","17UoHR0VCZeIrk+U6KV6b4yqhJYv978T10EeSvxfkE/8suwq",9051176829536850259,-235487814064438929,-6903828808329191749,-6956993786403273200>(),
                  this.De
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2b0hpi07nolsx","0ow4opQkofCFtAtFpRjSL0K8r6PUGsXm2HYvobhTxmk=",6177921811648420006,8547942550349512269,9218781919494480847,-7274052141657698926>()) {
                  case -1297135346:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   public static a a(JsonObject var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s93qq2e7f7eog","Ke+dJMi/+dqIJNjXZwXPvqQew2xh56EmT7A07ro6SEA=",-3416650069938963271,-7780950944573149672,-3569011531117166278,7080751892703579198>()) {
            case -1753251464:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1ewsivm3z73gm","v0tQJEaM/LG6liv2M+A+Ksqk3reqYQdd+JbK71EjR5394jw7",7588405504303476444,3240377505906177262,3357643426286769889,-3076661078591416207>(),
            (String)com.yiyiaddon.m.b.a<"souuneqeqeb91","GHhrhNNTQ5ZX3Msex/h5OjJ8zpKHrKZQ1emBs0MvyRT6GMLqtYrAZur4",9090273367268638634,-1974309150756974352,8898425321683124524,1096512336474817617>(),
            null
         );
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"shzh3t8zyr0q2","KGuJaeTVzZRt+dIJIh6wI5IlYX9pheKKQC18kQJSrVE=",-2264075009999272809,1692771130812267167,7567759766270710870,5914499463358714168>()) {
               case -179578000:
                  if (!var1.isBlank()) {
                     return new a(
                        var1,
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s3l04e95ylmerv","N/kMDOk2+rB/twXJz0rI2A3Y3os0hnpsm9fnB36/ZZKnRgWP",-7309509075827059313,-7637568415042862277,-6812434940811375319,5599336517320795944>(),
                           (String)com.yiyiaddon.m.b.a<"s12xssspsmdkoy","FwWQ8sAztmTrWCZsuRIA79JGksGa6pfTPTjTjcYjyVYWCopdRToeK24k5o8rUg==",3000707130969522421,8125336918660662869,-5283177972290486274,-6867511839339308148>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s10zdvfhh3wing","xg6TRpmiB+EqpZrMNSTXqOi7QTDvaqVNOL/+9eRSJKSd9005",-472897258558336926,971639113233958926,-7072372602346453350,139891818208414928>(),
                           (String)com.yiyiaddon.m.b.a<"s3398e1uhxxrlb","/fN27HoPmt2YvsURIi11ityTDsV2fQQeLLlZITrYKYA6ZUprQ/qRCzA15b6ixMz9",1208745918146275087,4665564398182461486,-821246594668866725,-945087442443094462>(),
                           (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s16vyh4h2ofy1j","U4HhWiG//hsEK2cGMZEu2AlYU0xiq7aDZmrTKAyu5gdpYQmr9YH53A==",-8710943376299177299,3135662738945492468,-808515802329427118,1476540835577200170>(),
                           (String)com.yiyiaddon.m.b.a<"s3fejet4ex1byd","z5LrsWwu5yTXVs6RD/LhZJYVkWkSc5FAQ5Emc1g/xWon82aZgHOzGN3yvoJ8kBoh0HM/1nv1jP1SkVLMbJc=",-9032975990411352559,2396959779537178141,-8502096078115356028,334393064710026820>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s1cfj347aeucki","4p6VOr19x/qkAvduubQPg7oL/kPf6okiape49etdpdyFz/UWOMAxbg==",4874868067709816375,-8678998961097363296,-7961377688576894308,4442585696485701238>(),
                           (String)com.yiyiaddon.m.b.a<"s2jf207kp7ghc4","o7OFe1UsyUsm8IdB3IeA893OM6OfdbTr2r/qh66ugvmH0XbMYRPtQmw//ZuJp2JVo5VdIq59JCesFA==",-599299834323248192,2456274740720468375,2196542681625992996,4547676256490794759>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"stvvclfld7thl","fvEVUZtcAR/Ja41qq70IJJFeB4NKieztElZ5HGjQttQ=",3456038509090716726,855797774566183158,8838120144291849498,6052227476762026509>(),
                           (String)com.yiyiaddon.m.b.a<"s4ccks9xa8i04","v9LEAv0QYUhQbkTtKfmPGDTjV04GLNk7GEKaRw08yrxrTmV8HRlWx2pV1PRo6Q==",-5314896839859318067,9116797399944927784,-1089550901441637085,3408058649664694557>(),
                           (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s3ckcfxfnkucs1","B7UxxVa9Rft5DIUbY8OTsa4Mc7I5PT6vnRzcY/jE3P0vmg==",4452762978239886055,2217292611826827908,-7100994274139826312,9097477612787050250>(),
                           (String)com.yiyiaddon.m.b.a<"s3dvjb21n1g37g","io9uap0PqPLAALBA3lWj8gDySpS2MKcKt5IBJb7CNmhFx6tZPeFXDg==",1431125367149920205,-575970957737785168,5427310063992653791,-4174120662623964041>(),
                           (String)com.yiyiaddon.m.b.a<"s3pd2kbbol9m09","0EbEe7E/OfUVu9TjrMwa9hGNFuF5qCqBmF/GWGSIXp7+hZuUOVYgQ8KP",-705123596733737348,-5297830017582182468,3845329793078682543,9089385110599417318>()
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s3oc2evmcfm0xd","Wa+8fXSkT0MZvWaBAy+LIPTW+CpXauO+Iuc8jMG58slhHg==",1298395397186620694,8967166744629499451,-1337118261567091630,5619662554445884543>(),
                           (String)com.yiyiaddon.m.b.a<"s2lza6s91cp97v","3u6f9xkl6KbSEucWqvqhi3YCaHA2+Fe8UywZCcde",-3922560885564408988,7316797394216586857,455885181028786967,-1756241614901357155>(),
                           0
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"sw7mkuleiowp2","t1uNstYX1/S8lJJZRTsIg9M1MtLnoOWr2anEjc//apBR+Q==",-7749999580384049546,-6381783771435241200,7686458312342448673,-1543880157256586491>(),
                           (String)com.yiyiaddon.m.b.a<"s11ffa0o3haifs","UcrGB7Bqmyaxu13rmrypM53jEjpIx5/5VmcvpEbs",-6923532185364522709,4622003056049058847,2683361419407714644,2855687347801489515>(),
                           0
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s1jfrbumlsl6uv","t7ukLetPvSFQhq6WLWm1hF7PhwJ4ygXspohU4epL+sLTAw==",8856793147327121935,-2358360787963372536,1898941977399784044,4219422678519681929>(),
                           (String)com.yiyiaddon.m.b.a<"s2q6zaskaj2z6j","0a9SVxWsESY3M2Juo4ZmJKaO8WEtqiL1dmHF7+Hq",-4711045403156110982,6987230679982781792,5551218611689907655,1812057773422062316>(),
                           0
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"sije8qjvgp8ij","W88cKuzo8WLS4NlzANrN1TxY9cb9raTORWYs0iGzLqp6LOm9",937053695536680546,-7279203208357157492,-4447042663754921106,-1620803921878569359>(),
                           (String)com.yiyiaddon.m.b.a<"s2x0e0nbjousj0","MdgJDP9/ISm2gdvYeQv86tPmDB/rOUfMAdzlJTZLLI8lH+WfZ76C0/JOCHS5Eeh3W8s=",1847136579932982499,7170978714736960322,-1373182778116327788,457842846369607320>(),
                           0
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s125jqz55amvey","EGnjs3bPt7nteK2iv5OkJP6kLoGNi0y9ychsbrx5gm+N9XAD",-269235457848065931,-7963275467796622202,-5155377729906010444,-3698954969397256572>(),
                           (String)com.yiyiaddon.m.b.a<"s3fuhl2cep8bhp","YlysXgk6QiM+2yFxcewl9YfMuYRUachW36l8ahqF4PT47dRGxRc70F9UCaoodeRokB7llX0R",-165824919129401607,-8697283078472834194,-8839289136334105515,-3353867669274760326>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"sa1zrte132td1","oEnXq6IDyoO73jOoKE1tTS/iByloExw5h3e72JNgJ3Lw6vriOAs=",8693147795922589145,5575198743349715775,-512490096539862791,6309494777404712098>(),
                           (String)com.yiyiaddon.m.b.a<"s2nfhjl7cdy6p2","I/U6jlb0b18y9LDgHD5uPhS5coArq9fWiST46ZoggzaFq7yXY6wQ8ytGr3kelmxJAIkH8iaUczHlhSVg",-8075470344851936904,4780553858035167764,-3002398366581096266,4930447259962340126>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s14i2ex8qqqk0p","KUxiKg54agjcFHH2eflv9rz49BcXNPo2M6h7PXYe/ynXqWppGCg=",-6215571872924679172,-8618462353508776914,1797349972195735527,-2245873847835875109>(),
                           (String)com.yiyiaddon.m.b.a<"s1ofaae7lfy75w","apJM1eFzyUX28ps4IxxEIs6DH4wBBMkI4QW0/LyysYyRS+Ee4GY4HPBbwlMHZULoUjZEeg==",3295198205433830473,8330498907859207145,-3598012443301056678,-1544047453947340113>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s1gy175fbv0mj6","NVO8x6iC0JdSfA0GHjT40UeIYIR0402jFbAHXZIx5VVM4VjD",-2790835868676846639,-4505569228878697280,-4729108105078438195,-6784771173949784545>(),
                           (String)com.yiyiaddon.m.b.a<"s3qf3dug7zaals","1TQ9G6tmmPP6JhKfFU4yhCBEHNyVrSqApntlSazvL29k0c10eOW2wDOgFhs+Ae1+rwYIl9IcZy8=",-5844519306932166933,2670506552167726661,-1311219369429544202,-7351213542232048544>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"slbnaqp4x87lk","g4EHyum3soQI0eq4g69JD+thcCURVvSHI7Tk1I7+1dEHel3P",-1998305288996134908,4734738090396638002,-9053178051278775358,6558035527969482349>(),
                           (String)com.yiyiaddon.m.b.a<"slotu8hqrsnsz","l84fGukchl+0IGPdJytytAOw1oisAhdC3kbKno6uKAnyd1xGtGqimF6nt+oWb/35GoZIsd0GmGD8FmjYaMc=",2036457552769034719,-8280597674474541427,242545335232683435,-568907690953886894>(),
                           null
                        ),
                        a(
                           var0,
                           (String)com.yiyiaddon.m.b.a<"s2534pv0ikq0lt","17UoHR0VCZeIrk+U6KV6b4yqhJYv978T10EeSvxfkE/8suwq",9051176829536850259,-235487814064438929,-6903828808329191749,-6956993786403273200>(),
                           (String)com.yiyiaddon.m.b.a<"s3b03x65fe5njn","vSncWUZuKzf8ymylbFdTedoPDg9GNyVMfsQDDdvs/JzGgQDaQrGhaQOM/p6Y5+nc6X4168N/wQs=",6982454171672313276,7947493408857644540,7794146783102024103,5130990751698244415>(),
                           null
                        )
                     );
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s33ox5vpd5mmg3","hjFcsNITkawMIUcR3yO9p/Rj5Oaneg1BgyL7u5WErXs=",4890235232618222101,-5439847932405280697,4330663821155828614,5683374191531126871>()) {
                        case 334013781:
                           return null;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return null;
         }
      }
   }

   public String dF() {
      String var10000;
      label43: {
         if (this.CY != null) {
            label36:
            switch ((int)com.yiyiaddon.m.b.a<"s2rpipjc4g0cl0","5VcWbfuTKCeay1PVVwIOZcKpws9LiOezLybDq4g+erM=",1600263540567824007,-4441208968107895709,8695881243077123672,-6882051631272822575>()) {
               case 1137957418:
                  if (!this.CY.isBlank()) {
                     var10000 = this.CY;
                     switch ((int)com.yiyiaddon.m.b.a<"s3d5cod0z1fz41","bPaxpw03Q859mxzOpKXa815Qz23ED2X/x6HAXhoZSlo=",-6512617378784581824,1100539193097189859,706015608248825341,-4562153431201487162>()) {
                        case 328633315:
                           break label43;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1g4xduv87kdcx","BJ/UqyHL8IhBcx6JGnbWD/O1tCo+CZQ2pf//vJaVMW8=",-6824697742644659539,-838639354077385083,-2734835271950754327,-8755216638445009653>()) {
                     case 1163084918:
                        break label36;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         var10000 = (String)com.yiyiaddon.m.b.a<"s3pd2kbbol9m09","0EbEe7E/OfUVu9TjrMwa9hGNFuF5qCqBmF/GWGSIXp7+hZuUOVYgQ8KP",-705123596733737348,-5297830017582182468,3845329793078682543,9089385110599417318>();
         switch ((int)com.yiyiaddon.m.b.a<"sk7i98fw1fsiz","89uMV9nPyTm+aWqIJAtXm8B7oqwuYH2MRAi4wyv/PWc=",2027987495025184801,-7646212321354332422,-5481346271489537264,896569293911812801>()) {
            case 1393449168:
               break;
            default:
               throw null;
         }
      }

      String var1 = var10000;
      if (this.CX == null) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s1mqponvh4k3g","QhqoOC6DUI+yJP/NdzNj5Ukpw5vUGQh3EYtnd12ay0M=",-7305674986271955045,-7829918177686042918,-155977575724650121,717984520381446696>()) {
            case -239781660:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>();
               switch ((int)com.yiyiaddon.m.b.a<"s3stqxrlstqhe","a6iRon4bDrebPKTiv2cJ0+Ilyt36WJMJKKjtL4Jq0m0=",7896743152831971191,5630921332354556450,-6929213018136107147,-4448586814609702048>()) {
                  case -1777825992:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.CX;
         switch ((int)com.yiyiaddon.m.b.a<"s25ee6ilvylgt1","/SCiAhlVvkVUBl0KuV307E4gZv3Gn5FpDH6fOKUAsuo=",-6103606436989437206,8187772647740826238,-1486353965323646360,2319080262352578764>()) {
            case 2090068098:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      return var1 + var2 + this.CS + this.sj + this.sk + this.sl;
   }

   public String m() {
      if (this.Db != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yi5df6t9skx9","IHDSuk22PJJSIISRYbDRitTwBWQ/xff7rOEbRZ+6ikQ=",-3910238736253097224,6093882588422818932,303201982702799167,-4140169798487048946>()) {
            case 2083072563:
               if (!this.Db.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2j0w4jzkm5vid","GlkP44itrXI5YtS/uChqcpOZsOUEDIhWcNpw1NmRGo4=",5154909587538576416,-5699914828888270252,-3643658308319338646,-2678253265073488089>()) {
                     case -2028298333:
                        return this.Db;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.Da != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2b6gntkzwoxbq","i7P+GgfnHZUh3Hh+xv2KtDDAroHEAlCiAeXoSJicIKk=",-5023955429792563171,-7991123949414998087,4581926998621966277,2495311371469080720>()) {
            case -1267636905:
               if (!this.Da.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"ssoi7yu7mo6su","eQNrrcx7igXS32iDLqn5TPrMWmd+JtaffgvUgxeBv68=",-8477952835532007788,-1460694297844345312,2110583883923594252,-1809326206405614205>()) {
                     case 1599076255:
                        return this.Da;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.CT != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sx42h7w465nds","zF1APYQ+cnwwpyaPVbluxRpWdMjanrWgguh3G3/wweM=",-6952544550895938782,-8896779620281245625,-5783652067703383321,-4161639270396599947>()) {
            case -2089411053:
               if (!this.CT.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s15yvjxr6xmkfq","s6qqjU0VX5z3ZxfwQL6y2Cd7yeQ6DkJyS2xfTsMtD34=",3408622106036375968,661324029462075807,889013625982137019,-6994072376338693643>()) {
                     case -1003253837:
                        return this.CT;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return this.CS;
   }

   public boolean fc() {
      return (String)com.yiyiaddon.m.b.a<"s2n1ugil8xe70o","ZT9eaEMHuw3eYVMyECGerZ2bqLIryEjuCWIqTIJWRamEsw==",-2990622032231905652,8497844734645624639,5508471718311969626,4837421906038382028>()
         .equals(this.Dd);
   }

   public String fI() {
      return a(this.CY, this.CX, this.CS, this.sj, this.sk, this.sl, this.CU, this.CW);
   }

   public String fJ() {
      return a(null, this.CX, null, this.sj, this.sk, this.sl, this.CU, this.CW);
   }

   private static String a(String var0, String var1, String var2, int var3, int var4, int var5, String var6, String var7) {
      String var8 = (
            var0 == null
               ? (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
               : var0
         )
         + (
            var1 == null
               ? (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
               : var1
         )
         + (
            var2 == null
               ? (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
               : var2
         )
         + var3
         + var4
         + var5
         + (
            var6 == null
               ? (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
               : var6
         )
         + (
            var7 == null
               ? (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>()
               : var7
         );

      try {
         byte[] var9 = MessageDigest.getInstance(
               (String)com.yiyiaddon.m.b.a<"s2lfrd79bjk3kf","79lMGnnye8H6gEXDfOBcATtsZ3FVfwuRfQQXGAGvC7W/12VIz0l6OSUA",-6843418032440531270,3034170734343238970,-6914289619694171575,-4934466490707472654>()
            )
            .digest(var8.getBytes(StandardCharsets.UTF_8));
         return HexFormat.of().formatHex(var9, 0, 6);
      } catch (Exception var10) {
         return Integer.toHexString(Objects.hash(var0, var1, var2, var3, var4, var5, var6, var7));
      }
   }

   public String fK() {
      String var10000;
      if (this.CU == null) {
         label44:
         switch ((int)com.yiyiaddon.m.b.a<"sehd4a83yzi5d","qa3Gr3Yg3JcTL3sSFa6yczx95FfnEetufc5JsnWwPYk=",1035929549016283081,9172024612150666089,5870916698548788604,-3556848295160752013>()) {
            case -1429679692:
               var10000 = (String)com.yiyiaddon.m.b.a<"s1yob61532veeq","Gt4bIGF+w9SIrfGMoR5Hq0rQNgTyDXmtTlSiqQ==",4273015437442747210,-8554479077587417852,-8148791015790701561,2689869599032623998>();
               switch ((int)com.yiyiaddon.m.b.a<"saa4jjmeng4i1","A/+/yEuU9dQUjpg1TpyA6lE2NKqty75vprfI2A7F5xc=",4196527659307518508,2271033161382894448,-2238146290521846304,5642101061348153368>()) {
                  case -345187454:
                     break label44;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.CU;
         switch ((int)com.yiyiaddon.m.b.a<"s3oha7eu7j1nly","6AJli/A9XaSLSaVgrnLyZysccLJkaFx6qUw42xvK1g0=",-8753464781465462384,7630536826667479767,6957147035001633660,-2690048429284357967>()) {
            case -1970847656:
               break;
            default:
               throw null;
         }
      }

      String var1 = var10000;
      if (var1.startsWith(
         (String)com.yiyiaddon.m.b.a<"s3qs4bt4umxa8p","wsJA+hBajLBmlp8F5xkoIs9xxmOW0LEwz85onN/y63vxfNhDC2Q9XA==",5885926643557031325,2218242903821099427,-8067204100090362668,2827111947585924783>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s23w3sw0deq2ex","dUF9o0Hm6tGhnaYXwfA/GcjjQGgnfCZDPAItBwHJ6hg=",-3781431456497381266,-1692366034898588704,4524174086828658130,-2928842591770313656>()) {
            case 558511257:
               int var2 = var1.indexOf(125);
               if (var2 > 6) {
                  label37:
                  switch ((int)com.yiyiaddon.m.b.a<"s1noas64hpj9jz","7xULWEq+L0jvQNI43ptt3mL6l0z9Euha+eI3kqkFMps=",-8792369150866969779,-3990532244218907124,-4326661314381082894,-2676670824222019393>()) {
                     case 1809644719:
                        var1 = var1.substring(6, var2) + var1.substring(var2 + 1);
                        switch ((int)com.yiyiaddon.m.b.a<"s1qbhnu74cprsc","0Q3bEQEDBSAR2uLimaX0InOfZ9ocRFvh8TAIrTdT0Pg=",-6831629587935715167,-1019592852777814311,867496177399521024,5783339268808569948>()) {
                           case 1392262221:
                              break label37;
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

      if (var1.isBlank()) {
         switch ((int)com.yiyiaddon.m.b.a<"s18ud5uoqk2ca","gZcSNSBKUKwscCr6TvnKDBQ2ihnzT0gdXQ5VzqpKLQA=",7476329408510365275,6463766163969546475,4331579031334370630,5850905856992573492>()) {
            case 1746547661:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3g2ij7g0rz73a","BjE3GxCTAdHG+gXCf3jSToXr1i066Ym/Ma1NhS0H",-2102099867727934584,-6301035718036504072,5001365798854927839,-572529927367556546>();
               switch ((int)com.yiyiaddon.m.b.a<"smfpvyuczhkcn","OsZNMmbP7z+vFfHdJXpCOucw3PBsn+RRDzVCzpNGYbg=",6627293721208428024,4041980037510425027,-2381011356014354267,-2358012421972399887>()) {
                  case -798395911:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2wnr0n4nzkrm3","MY7rFFoBABU5jLge/u6bLO2ZU7CofgS/X0GC42Dd1OQ=",-4370783900383571073,775582934300559158,955502366951210312,-3375251673476290947>()) {
            case 2105126187:
               return var1;
            default:
               throw null;
         }
      }
   }

   private static String a(JsonObject var0, String var1, String var2, String var3) {
      if (var0.has(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rdhovurvjbjm","xbJT43IsyMA1t2WUpgVGN6X4fYIQ7dNVn3rxNxKzbIM=",1918531379805342727,5349755213177538794,4573892708007549515,6620777273415889908>()) {
            case -744596085:
               if (!var0.get(var1).isJsonNull()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2pvd553hf4yqz","34yn5ohMyObKPnYPy2ekok5qHWVeYlEV6ThnZtTLhro=",2068303035251390539,-4197481036021384883,-5794696451870777491,-378472318803969128>()) {
                     case -2088522451:
                        return var0.get(var1).getAsString();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var0.has(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1km6zrlrq1f7q","8fnjzMtnjEzPcbe08o0WB3q+w4ZT/6xTNPYr9TsdAMc=",8775343504611733325,-416773307960290583,-678900434205355488,-7446542445001041208>()) {
            case 696995842:
               if (!var0.get(var2).isJsonNull()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31si5d8840xnp","vMzMZzgFZJBt7ZBwpiqx8ii3GAVMlWwj810NtHXmQjQ=",-4584201156388064320,-2399069711889260726,7430608411265404104,-1569065470218425020>()) {
                     case 294658247:
                        return var0.get(var2).getAsString();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var3;
   }

   private static int a(JsonObject var0, String var1, String var2, int var3) {
      if (var0.has(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"sde0uzcxzcpx9","1ZeqCx2D7Az7llnJ+tPqdHgZ2uUEL+MOV0tmAGtftpQ=",7762225656555130281,-6773094773472807912,684409473929405665,8216341038011091459>()) {
            case 1860386150:
               if (!var0.get(var1).isJsonNull()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s120jfsc0soeti","0rOVED/S0dqx/QKTgJVWS3/PhReCyyMXVYWEiUvn0Sk=",-7386723431013410446,6017592101698471737,-1514470157012539414,77710156679477466>()) {
                     case 1250115697:
                        return var0.get(var1).getAsInt();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var0.has(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2b1v1pdrhg337","EbbAepxnH0SOpVKiL89ieD59KmCbaGr0nihZKSVAxDE=",5313040063665712913,5962015205922115979,4283983781121294428,5230724201254363993>()) {
            case 922575981:
               if (!var0.get(var2).isJsonNull()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27lv87nvlfwqd","6GkA7LdoLV0kJCg6PeRuChKUrm546WLGCdCWCRJO2xA=",5049314142750247867,-9172736090069946542,8909474601693966699,8034995298664340553>()) {
                     case 1097557228:
                        return var0.get(var2).getAsInt();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var3;
   }

   public String fL() {
      return this.CS;
   }

   public String fM() {
      return this.CT;
   }

   public String fN() {
      return this.CU;
   }

   public String fO() {
      return this.CV;
   }

   public String fP() {
      return this.CW;
   }

   public String bU() {
      return this.CX;
   }

   public String fG() {
      return this.CY;
   }

   public int aj() {
      return this.sj;
   }

   public int ak() {
      return this.sk;
   }

   public int al() {
      return this.sl;
   }

   public int aA() {
      return this.sm;
   }

   public String ds() {
      return this.CZ;
   }

   public String dr() {
      return this.Da;
   }

   public String fQ() {
      return this.Db;
   }

   public String fR() {
      return this.Dc;
   }

   public String fS() {
      return this.Dd;
   }

   public String fT() {
      return this.De;
   }
}
