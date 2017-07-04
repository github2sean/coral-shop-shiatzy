一级分类
insert into t_goods_category (id,rank,parent_id,prototype_id,name,en_name,thumb,is_valid,level)
select  id,rank,0,0,title,en_title,image,is_visible,1 from out_products_themes 
update t_goods_category set thumb = replace('[{"alt":"thumb","file":"path"}]','path',thumb)
二级分类
insert into t_goods_category (id,rank,parent_id,prototype_id,name,en_name,thumb,is_valid,level)
select  (id+15,rank,theme_id,0,title,en_title,image,is_visible,2) from out_products_categories 
材质
insert into t_goods_skins(id,rank,is_valid,title,en_title,image,create_time,update_time) 
select id ,rank,is_visible,title,en_title,image,created_at,updated_at from outer_products_skins
商品
预处理
update outer_products_items set price = replace(price,',','')
update outer_products_items set price = replace(price,'新品未定价','0')	
update outer_products_items set price = '0' where price = ''
insert into t_goods (id,name,en_name,color_ids,prototype_id,category_ids,is_published,thumb,price,dis_price,is_sale,is_pre)
	select id,title,en_title,color_search,1,category_id,is_visible,image,price,price,0,0 from outer_products_items

update t_goods as g LEFT JOIN outer_products_items as i on s.item_id = i.id  set s.goods_id = i.goods_id

update t_goods set color_ids =replace(color_ids,'"','')
update t_goods set category_id = 17
新增字段category_ids
更新分类id：updateCategoryIds()
商品图片
update t_goods set thumb = replace('[{"alt":"thumb","file":"path"}]','path',thumb)

商品项目
insert into t_goods_item (id,rank,is_valid,goods_id,color_id,skin_id,goods_no,name,en_name,thumb,color_value,price,
	discount_price,description,en_description,related_goods,create_time,update_time,is_sale) 
select id,rank,is_visible,item_id,color_id,skin_id,product_no,title,en_title,image,show_color,price,dis_price,content,en_content,related_product,created_at,updated_at,0 from outer_products_specs

update t_goods as g LEFT JOIN outer_products_items as i on i.id = g.id  set g.is_published = i.is_visible

update t_goods_item set thumb = replace('[{"alt":"thumb","file":"path"}]','path',thumb)
update t_goods_item set photos = thumb
关联颜色updateGoodsItemColor()

商品sku
update outer_products_specs_sizes set stock = '0' where stock =''
insert into t_goods_sku (id,goods_id,item_id,goods_no,size,is_valid,quantity,create_time,update_time,is_pre)
select id,0,spec_id,model_no,size_type,is_visible,stock,created_at,updated_at,0 from outer_products_specs_sizes

update t_goods_sku set specifications =replace('{"size":"#size"}','#size',size)
update t_goods_sku  set size  = replace(size,'均码/One Size','均码')
update t_goods_sku as s LEFT JOIN t_goods_item as i on s.item_id = i.id  set s.goods_id = i.goods_id
程序更新 specifications	 updateSkuSize（）
商品原型
insert into t_goods_prototype (id,name,code,is_valid,create_time) select id,title,en_title,is_visible,created_at from outer_products_sizes
尺寸规格
insert into t_goods_prototype_specification (name,en_name,rank,prototype_id,is_color) select '尺寸','size',id,0 from t_goods_prototype
尺寸规格选项列表
insert t_goods_prototype_specification_option(id,specification_id,name,en_name,rank) 
	select id,size_id,title,title,rank  from outer_products_sizes_infoes
