package client

import shared.Link

class TableRow(val link:shared.Link){
  
  def articleLink:String = {
    link.data.url.replace("""\/""", """/""")
  }
  
  def articleTitle:String = {
    link.data.title
  }
  
  def articleSiteName:String = {
    link.data.domain
  }
  
  def posterName:String = {
    link.data.author
  }
  
  def posterLink:String = {
    """https://www.reddit.com/user/"""+link.data.author
  }
  
  def subredditName:String = {
    """/r/"""+link.data.subreddit
  }
  
  def subredditLink:String = {
    """https://www.reddit.com/r/"""+link.data.subreddit
  }
  
  def commentCount:Int = {
    link.data.num_comments
  }
  
  def commentLink:String = {
    """https://www.reddit.com"""+link.data.permalink.replace("""\/""", """/""")
  }
  
  def asHtml:String = {
    """<tr>"""+{
         """<div class="col-md-12">"""+{
           """div id="sub_row" class="row">"""+{
             """<div class="col-sm-12"  style="font-size: 16px; vertical-align: middle;">"""+{
               """<a id="link_seen_button" href="javascript:remove_row(0)">"""+{
                 """&nbsp;<span class="glyphicon glyphicon-remove-sign" style="color: white;"></span>&nbsp;"""
                 }+"""</a>"""+{
                   """<a id="the_link" href=""""+this.articleLink+"""">"""+this.articleTitle+"""</a>"""+"""&nbsp;<a href=http://""""+this.articleSiteName+"""" style="font-size: 12px;">["""+this.articleSiteName+"""]</a>""" 
                 }
             }+"""</div>"""
           }+"""</div>"""
         }+"""</div>"""+"""<div class="row">"""+{
            """<div class="col-sm-12 col-xs-offset-1">"""+{
                  """by """+{
                    """<a href=""""+this.posterLink+""""">"""+this.posterName+"""</a>"""
                  }+"""@"""+{
                    """<a href=""""+this.subredditLink+""""">"""+this.subredditName+"""</a>"""
                  }+"""|"""+{
                    """<a href=""""+this.commentLink+"""">"""+this.commentCount.toString+""" comments</a>"""
                  }
            }+"""</div>""" 
          }+"""</div>"""
    }+"""</tr>"""
  }
}


/*


<body>
<nav class="navbar navbar-fixed-top">
    <div><button class="btn btn-warning pull-right" style="margin-right: 1%;" onclick="logout()">Log Out</button></div>
</nav>
<div class="row">
<div class="col-md-12" style="background color: #F0FFFF; margin-top: 3%">


<div id="link_table">

*/