package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.k;
import com.yiyiaddon.l.b.r;
import com.yiyiaddon.l.j.i;
import com.yiyiaddon.l.j.n;
import net.minecraft.client.gui.screens.Screen;

public final class b extends f {
   private final com.yiyiaddon.l.g.a.d ad;
   private final Runnable k;

   public b(String var1, com.yiyiaddon.l.g.a.d var2, Screen var3) {
      this(var1, var2, var3, null);
   }

   public b(String var1, com.yiyiaddon.l.g.a.d var2, Screen var3, Runnable var4) {
      super(var1, var3);
      this.ad = var2;
      this.k = var4;
      this.G();
   }

   @Override
   public void removed() {
      try {
         if (this.k != null) {
            this.k.run();
         }
      } finally {
         super.removed();
      }
   }

   private void G() {
      this.bw(
         (String)com.yiyiaddon.m.b.a<"s3twl7yl8slcv2","t/FTzL3U6QCkli7rUPQPnJJKAOc3XiExJAnUIzRxhRG1u13h1fz5hJLmyoQ=",-5542521802175830382,8472663597789987861,-3207029669685297302,-532997240284859315>()
      );
      this.d().a(new com.yiyiaddon.l.b.f(this.ad));
      this.kN();
      this.bw(
         (String)com.yiyiaddon.m.b.a<"s2xu01yev4nuzt","vudk0XsW9EgwlYsyu8UHhiaLBNOoT0Xub8aQo6WTKmpHUnpP37O/RHHqwUM=",-3662197433043992396,-1502439116081852357,-2463644659831621821,-3171410288089628062>()
      );
      this.d().a(new k(this.ad));
      this.kN();
      this.bw(
         (String)com.yiyiaddon.m.b.a<"s1f9luhuouennm","9G1j5+rhQiqw8hWuZL0e9/7/kUdY3QXhD6kDBX5/W8f0U/kUdW17M3nVWZKs+SczfUB5QA==",-7941106467590965118,3756259879231595830,-5420721049231988048,-2136780725210781585>()
      );
      this.d().a(new r(this.ad));
      this.kN();
      this.bw(
         (String)com.yiyiaddon.m.b.a<"sprqmb7h0b7uu","TAltIN6pas9bIrjgrAZ8MbiLo5EWzF3LEcf7uIcv0p7f54xUWfIo1KQGC5c=",8706992117688452571,-8241506555276759173,4976721984864796085,-7074098113242106893>()
      );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"s1p0z40sy3o366","PhqbR+NdsSw/8NUPPHZa8QbSpuBTSAzfAa/9Uv+n",-2584806414629912955,4911168368391421568,-8840534042213953503,6485334698955159029>(),
               () -> (String)com.yiyiaddon.m.b.a<"s3lli2nnsz1mjn","FqkvmxRXjlvKl+SkVJxu2AiN/TwIEWNFEpwgPZE6lbA5loD+WLZsdM1H",-1307779445929280786,-7809546444834283789,-3145799110247053045,2501696471316688567>(),
               this.a(16)
            )
         );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"s1jlrxl7kyehuq","7Iia700bJmys3RjQZCFIklNYAfhS0ugD3UbUAZ7L",-3098280608997562075,7426423813060025026,-7655484681614388916,7363814661001620137>(),
               () -> (String)com.yiyiaddon.m.b.a<"s3lli2nnsz1mjn","FqkvmxRXjlvKl+SkVJxu2AiN/TwIEWNFEpwgPZE6lbA5loD+WLZsdM1H",-1307779445929280786,-7809546444834283789,-3145799110247053045,2501696471316688567>(),
               this.a(8)
            )
         );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"ssv3noyha1vzm","wfhtZK2DiTZouXQUzFvs4A377/hG9ffR3yUupHNK",-2453295717576715514,7197623772070665186,6892650480392050480,-5574641215300976837>(),
               () -> (String)com.yiyiaddon.m.b.a<"s3lli2nnsz1mjn","FqkvmxRXjlvKl+SkVJxu2AiN/TwIEWNFEpwgPZE6lbA5loD+WLZsdM1H",-1307779445929280786,-7809546444834283789,-3145799110247053045,2501696471316688567>(),
               this.a(0)
            )
         );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"s2igdd3ugovlsb","OXoN1WuAY9da8bRFMVrDDQoWqF619J0ofn06QnvHuRg=",1899157621656694627,5896077060978437115,-1582111693099897354,702562461386040009>(),
               () -> (String)com.yiyiaddon.m.b.a<"sazi43babtddj","irMqEbm6HbCcN/2YhkttmZSMvPKJtu2Ln/5gPOT2RovuLfAZPoPFg4qAblOwd+q96fJpDTh/",-3133523320737222338,-4773961914459831842,-8705609241767034639,-3616968973374401096>(),
               new i(
                  0.0,
                  255.0,
                  1.0,
                  (String)com.yiyiaddon.m.b.a<"ss72w25iyvpez","JwJzzIqT69H0CA3Znkt7jMd67R6BttyVcLixuQWhb8/PBclm",-8902365334482582531,3098577973110291115,-2203016931540278808,-3293647624370133334>(),
                  () -> (double)this.ad.ei(),
                  var1 -> this.ad.c(var1.intValue())
               )
            )
         );
      this.kN();
      this.bw(
         (String)com.yiyiaddon.m.b.a<"slc33xebmoqoo","7WPfqtZoY7XcAaxHxUrsgcXe1UU/P9GwiqVNm5Hyp3/GyyqZ0MMNU4+kCw0=",-6805792244117290673,-401290932479980909,-2906582413059926607,4932153199246139095>()
      );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"s2xp4pybygm2mb","eTDbSwdWalYTQhKAdszKwT4Nw2aUnTNqcvUAPKpFTok=",-4823247649811112477,-3047127032937907455,-7130030700561552824,3019457608733324035>(),
               () -> (String)com.yiyiaddon.m.b.a<"s214zc8iesz6dh","9I7KlcD+gZBb2/ovhD4NK95N2vbxKFXWBVjj6TozDcZS2IEVb3BsVTNP3nS68w==",8981695558408006245,-5396781431222333959,-2719157750810976827,-4459504741672542523>(),
               new n(this.ad::gj, var1 -> this.ad.a(var1))
            )
         );
      this.d()
         .a(
            new h(
               (String)com.yiyiaddon.m.b.a<"s1eji61b3mxdbr","F87Apsq16xuvzI4/U6QCQmZ15/5S4Dhi2kjJ4q234iU=",-4241130517885245105,-8246419179239206037,6707385901702054589,-3352660500172055577>(),
               () -> (String)com.yiyiaddon.m.b.a<"s3c8k8pe16zsvu","TFqJIh7v52BQVPR7riMhDJk8yu39xaEjzjcDPE4JBTQxDr7/XsLWMBkMxiI7PBGTjsqxoIOIfbF8wA==",-2247464940712917164,-4915003636375105388,-6519019453489438758,314888953171424777>(),
               new i(
                  0.02,
                  4.0,
                  0.02,
                  (String)com.yiyiaddon.m.b.a<"s2lqqflhtk3s12","ZGDC2jWBJRzJzpSJn398xzqWoEKFqw/aJy02Qvvb+SXzTOvn",7378265997336763001,-651396579750237728,2880861657741536629,7593003984565783738>(),
                  this.ad::l,
                  var1 -> this.ad.a(var1)
               )
            )
         );
      this.kO();
      this.a(
         (String)com.yiyiaddon.m.b.a<"sylf9okaawoga","gAmW+OVV1/VezIE/yjZVw5RsKJAMhWKnbvqRG1jsDx6LPZhx",1537572924052382514,-1173042023401080433,-5019783019748867036,-5414010337562303003>(),
         this::kR
      );
   }

   private i a(int var1) {
      int var2 = 255 << var1;
      return new i(
         0.0,
         255.0,
         1.0,
         (String)com.yiyiaddon.m.b.a<"ss72w25iyvpez","JwJzzIqT69H0CA3Znkt7jMd67R6BttyVcLixuQWhb8/PBclm",-8902365334482582531,3098577973110291115,-2203016931540278808,-3293647624370133334>(),
         () -> (double)(this.ad.eh() >> var1 & 0xFF),
         var3 -> {
            this.ad.b(this.ad.eh() & ~var2 | (var3.intValue() & 0xFF) << var1);
            this.ad.a(false);
         }
      );
   }
}
