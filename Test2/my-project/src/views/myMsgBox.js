import ElementPlus from 'element-plus';
import MyDialog from './MyForm.vue';
import { h, ref, createApp } from 'vue';

export function myMsgBox(text, title, options) {
  return new Promise((resolve, reject) => {
    // 保存组件实例
    let myDialogRef = ref();
    // 创建容器
    const mountNode = document.createElement('div');
    // 将容器插入到body内
    document.body.appendChild(mountNode);
    // 创建节点
    const app = createApp({
      render() {
        return h(MyDialog, {
          ref: myDialogRef,
          title,
          text,
          options,
          onSubmit: (data) => {
            resolve(data);
            app.unmount(mountNode);
            mountNode.remove();
          },
          onClosed: () => {
            reject();
            app.unmount(mountNode);
            mountNode.remove();
          }
        });
      }
    });
    // 由于内部使用了el-dialog所以必须挂载否则解析错误
    app.use(ElementPlus);
    // 挂载容器
    app.mount(mountNode);
    // 打开弹窗
    myDialogRef.value.showDialog();
  });
}
