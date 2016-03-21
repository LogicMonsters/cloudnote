// JavaScript Document
function formate_name(e){
	e=e.replace(/</g,'&lt;');
	e=e.replace(/>/g,'&gt;');
	return e;
}
function check_null(s){
	s=s.replace(/ /g,'');
	s=s.length;
	return s;
}
function yc(a){
	a.hide();
}
// get value from URL
function get_activity_id(){
	var url=window.location.hash;
	url=url.replace(/#/,'');
	global_ac_id=url;
	get_list(url);
}
//get notebook list
function get_nb_list(){
	notebookNormalList(successFunc_nblist,errorFunc_nblist);
}

//get special notebook list
function get_spnb_list(){
	notebookSpecialList(successFunc_spnblist,errorFunc_spnblist);
}

$(function(){
	get_nb_list();
	//new notebook
	var dom,flag;
	$(".profile-username").html(getCookie(UserName));
	$(document).on("click", "#add_notebook",
    function(a) {
		$('#can').load('./alert/alert_notebook.html',function(){
			$('#input_notebook').focus();
		});
		$('.opacity_bg').show();
    }),
    
    
	//cancel creating notebook
	$(document).on("click", ".close,.cancle",
    function(a) {
		$('#input_notebook,#input_note').val('');
        $('.modal.fade.in').hide();
        $('.opacity_bg').hide();
    }),
    
    
	//create notebook
	$(document).on("click", "#modalBasic .btn.btn-primary.sure",
    function(a) {
		var get_name=$('#input_notebook').val();
		var s_num=check_null(get_name);
		get_name=formate_name(get_name);
		if(get_name!=null&&get_name!=''&&s_num!=0){
			var createNoteBean={
					cnNotebookName:get_name
			};
			addnote(createNoteBean,successFunc_addnb,errorFunc_addnb);			
		}
    }),
    
    
	//delete notebook
	$(document).on("click", "#first_side_right .btn_delete",
    function(a) {
		$('#can').load('./alert/alert_delete_notebook.html');
		$('.opacity_bg').show();
		dom=$(this).parents('li');
		//confirm
		$(document).on('click','#modalBasic_6 .btn.btn-primary.sure',function(){

			if($('#second_side_right ul').children().length >0){
				alert("There are notes in current notebook, can't be deleted!");
				return ;
			}
			dom_data=dom.data('cnNotebookId');
			$('.close,.cancle').trigger('click');
			var delete_deletenb={
					cnNotebookId:dom_data
			};
			deletenote(delete_deletenb,successFunc_deletenb,errorFunc_deletenb,dom);
		});

		return false;
    }),
    
    
	//click notebook --> load note list
	$(document).on("click", "#pc_part_1 li",
    function(a) {
		$('#pc_part_2,#pc_part_3').show();
		$('#pc_part_2 ul').empty();
		$('#pc_part_4,#pc_part_5,#pc_part_6,#pc_part_7,#pc_part_8').hide();
		$('#rollback_button,#like_button,#action_button').removeClass('clicked');
		$(this).siblings('li').children('a').removeClass('checked');
		$(this).children('a').addClass('checked');
		var nom_info=$(this).data('cnNotebookId');
		$('#notebookId').data('cnNotebookId',nom_info);
		getNormalNoteList(nom_info,successFunc_normalbl,errorFunc_normalbl);
    }),
    
    
	//double click notebook  --> rename
	$(document).on("dblclick", "#pc_part_1 li:gt(0)",
    function(a) {
		dom=$(this);
		$('#can').load('./alert/alert_rename.html',function(){
			$('#input_notebook_rename').focus();
			$('#modalBasic_4 .sure').data({'dom':dom});
		});
		$('.opacity_bg').show();
		flag=$(this).children('a').children('button').length;
		$(document).on("click",'#modalBasic_4 .sure',function(){
			var get_old_name=dom.text();
			var get_nb_id=dom.data('cnNotebookId');
			var get_nb_type=dom.data('cnNotebookTypeId');
			var get_new_name=$('#input_notebook_rename').val();
			var get_name=$('#input_notebook_rename').val()?get_new_name:get_old_name;
			var s_num=check_null(get_name);
			get_name=formate_name(get_name);
			if(get_name!=get_old_name&&get_name!=null&&get_name!=''&&s_num!=0){
				var updateNoteBean={
						cnNotebookName:get_name,
						cnNotebookId:get_nb_id,
						cnUserId:getCookie(cookie_key),
						cnNotebookTypeId:get_nb_type
				};
				updateNoteBook(updateNoteBean,successFunc_renamenb,errorFunc_renamenb,dom);
				$('.close,.cancle').trigger('click');
			}
		});
    })

	
});