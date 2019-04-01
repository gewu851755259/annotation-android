package yml.com.annotation;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import yml.com.annotation.anno.AnnotationParse;
import yml.com.annotation.anno.FindViewById;
import yml.com.annotation.anno.SetOnClickListener;

public class MainActivity extends AppCompatActivity {

    @FindViewById(R.id.tv_hello_world)
    private TextView mHello;

    @FindViewById(R.id.btn_test)
    @SetOnClickListener(id = R.id.btn_test, methodName = "changeHelloContent")
    private Button mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 解析注解 2017/4/14 09:22
        AnnotationParse.parse(this);
        Toast.makeText(getApplicationContext(), "hello id:" + mHello.getId(), Toast.LENGTH_SHORT).show();


        testNull(null, null, 1, 0.5f);
    }

    private void changeHelloContent() {
        mHello.setText("Hello World!".equals(mHello.getText())
                ? "你好 世界！" : "Hello World!");
    }

    /**
     * @param var1 可以为空
     * @param var2 不能为空
     */
    private void testNull(@Nullable String var1, @NonNull String var2,
                          @IntRange(from = 0, to = 255) int var3,
                          @FloatRange(from = 0.0f, to = 1.0f) float var4) {

    }

}
